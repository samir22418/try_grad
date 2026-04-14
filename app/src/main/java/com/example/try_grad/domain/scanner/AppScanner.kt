package com.example.try_grad.domain.scanner

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.example.try_grad.data.models.AppInfo
import com.example.try_grad.data.models.RiskLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Core scanning engine.
 *
 * Workflow for each installed package:
 *  1. Read package metadata via [PackageManager].
 *  2. Check [TrustedAppsWhitelist] — if trusted, lock score to [TrustedAppsWhitelist.TRUSTED_APP_SCORE]
 *     and skip the heavyweight risk calculation entirely.
 *  3. For untrusted apps, extract dangerous permissions and compute a weighted risk score.
 *  4. Return an [AppInfo] ready for storage or display.
 */
class AppScanner(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager

    // ─── Public API ───────────────────────────────────────────────────────────

    suspend fun scanAllApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val installedApps = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getInstalledPackages(
                PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        }

        installedApps.mapNotNull { packageInfo ->
            try {
                extractAppInfo(packageInfo)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun scanSingleApp(packageName: String): AppInfo? = withContext(Dispatchers.IO) {
        try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            }
            extractAppInfo(packageInfo)
        } catch (e: Exception) {
            null
        }
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private fun extractAppInfo(packageInfo: PackageInfo): AppInfo? {
        val applicationInfo = packageInfo.applicationInfo ?: return null
        val appName = packageManager.getApplicationLabel(applicationInfo).toString()
        val isSystemApp = (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
        val permissions = packageInfo.requestedPermissions?.toList() ?: emptyList()

        // ── Trusted app fast-path ─────────────────────────────────────────────
        val isTrusted = TrustedAppsWhitelist.isTrusted(packageInfo.packageName)
        if (isTrusted) {
            return AppInfo(
                packageName      = packageInfo.packageName,
                appName          = appName,
                versionName      = packageInfo.versionName ?: "Unknown",
                versionCode      = resolveVersionCode(packageInfo),
                installDate      = packageInfo.firstInstallTime,
                updateDate       = packageInfo.lastUpdateTime,
                isSystemApp      = isSystemApp,
                permissions      = permissions,
                // Still record dangerous permissions for informational display,
                // but they won't affect the risk score.
                dangerousPermissions = getDangerousPermissions(permissions),
                riskScore        = TrustedAppsWhitelist.TRUSTED_APP_SCORE,
                riskLevel        = RiskLevel.LOW,
                isTrusted        = true,
                icon             = loadIcon(applicationInfo)
            )
        }

        // ── Unknown / untrusted app — full risk analysis ───────────────────────
        val dangerousPermissions = getDangerousPermissions(permissions)
        val riskScore = calculateRiskScore(
            permissions          = permissions,
            dangerousPermissions = dangerousPermissions,
            isSystemApp          = isSystemApp
        )

        return AppInfo(
            packageName      = packageInfo.packageName,
            appName          = appName,
            versionName      = packageInfo.versionName ?: "Unknown",
            versionCode      = resolveVersionCode(packageInfo),
            installDate      = packageInfo.firstInstallTime,
            updateDate       = packageInfo.lastUpdateTime,
            isSystemApp      = isSystemApp,
            permissions      = permissions,
            dangerousPermissions = dangerousPermissions,
            riskScore        = riskScore,
            riskLevel        = RiskLevel.fromScore(riskScore),
            isTrusted        = false,
            icon             = loadIcon(applicationInfo)
        )
    }

    // ─── Version code compatibility shim ─────────────────────────────────────

    private fun resolveVersionCode(packageInfo: PackageInfo): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()
        }
    }

    // ─── Icon loading ─────────────────────────────────────────────────────────

    private fun loadIcon(applicationInfo: ApplicationInfo) = try {
        applicationInfo.loadIcon(packageManager)
    } catch (e: Exception) {
        null
    }

    // ─── Dangerous permissions catalogue ─────────────────────────────────────
    //
    // These are Android runtime permissions classified as PROTECTION_DANGEROUS
    // by the Android framework. An untrusted app requesting any of these may
    // be attempting to access sensitive user data.

    private val dangerousPermissionList = listOf(
        // Contacts
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        // Calendar
        "android.permission.READ_CALENDAR",
        "android.permission.WRITE_CALENDAR",
        // Phone / Calls
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.PROCESS_OUTGOING_CALLS",
        "android.permission.CALL_PHONE",
        "android.permission.READ_PHONE_STATE",
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.ANSWER_PHONE_CALLS",
        // SMS
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.SEND_SMS",
        "android.permission.RECEIVE_WAP_PUSH",
        "android.permission.RECEIVE_MMS",
        // Camera
        "android.permission.CAMERA",
        // Microphone
        "android.permission.RECORD_AUDIO",
        // Location
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        // Storage
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.MANAGE_EXTERNAL_STORAGE",
        // Sensors / Biometrics
        "android.permission.BODY_SENSORS",
        "android.permission.BODY_SENSORS_BACKGROUND",
        "android.permission.USE_BIOMETRIC",
        // Media (Android 13+)
        "android.permission.READ_MEDIA_IMAGES",
        "android.permission.READ_MEDIA_VIDEO",
        "android.permission.READ_MEDIA_AUDIO",
        "android.permission.ACCESS_MEDIA_LOCATION",
        // Nearby devices (Android 12+)
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.UWB_RANGING",
        // Activity recognition
        "android.permission.ACTIVITY_RECOGNITION"
    )

    private fun getDangerousPermissions(permissions: List<String>): List<String> {
        return permissions.filter { requested ->
            dangerousPermissionList.any { known -> requested == known }
        }
    }

    // ─── Risk score calculation ───────────────────────────────────────────────
    //
    // Scoring model (max 100):
    //   • Base:           +5 per dangerous permission, capped at 50
    //   • SMS access:     +12 each (READ_SMS / SEND_SMS) — highest abuse potential
    //   • Phone access:   +10 (READ_PHONE_STATE, CALL_PHONE)
    //   • AV/Mic/Camera:  +10 each
    //   • Location:       +8 (FINE) / +4 (COARSE)
    //   • Contacts:       +8
    //   • Internet:       +5
    //   • Network state:  +3
    //   • System app:     ×0.3 reduction (generally safer)

    private fun calculateRiskScore(
        permissions: List<String>,
        dangerousPermissions: List<String>,
        isSystemApp: Boolean
    ): Int {
        var score = 0

        // Base score from dangerous permission count (capped at 50)
        score += (dangerousPermissions.size * 5).coerceAtMost(50)

        // High-impact individual permissions
        if ("android.permission.READ_SMS"  in permissions) score += 12
        if ("android.permission.SEND_SMS"  in permissions) score += 12
        if ("android.permission.RECEIVE_SMS" in permissions) score += 10
        if ("android.permission.READ_PHONE_STATE" in permissions) score += 10
        if ("android.permission.CALL_PHONE" in permissions) score += 10
        if ("android.permission.CAMERA"    in permissions) score += 10
        if ("android.permission.RECORD_AUDIO" in permissions) score += 10
        if ("android.permission.ACCESS_FINE_LOCATION" in permissions) score += 8
        if ("android.permission.ACCESS_BACKGROUND_LOCATION" in permissions) score += 8
        if ("android.permission.READ_CONTACTS" in permissions) score += 8
        if ("android.permission.MANAGE_EXTERNAL_STORAGE" in permissions) score += 8
        if ("android.permission.ACCESS_COARSE_LOCATION" in permissions) score += 4
        if ("android.permission.INTERNET" in permissions) score += 5
        if ("android.permission.ACCESS_NETWORK_STATE" in permissions) score += 3

        // System apps are built into the firmware and generally audited
        if (isSystemApp) {
            score = (score * 0.3).toInt()
        }

        return score.coerceIn(0, 100)
    }
}