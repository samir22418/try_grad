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

class AppScanner(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager

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

    private fun extractAppInfo(packageInfo: PackageInfo): AppInfo? {
        val applicationInfo = packageInfo.applicationInfo ?: return null
        val appName = packageManager.getApplicationLabel(applicationInfo).toString()
        val isSystemApp = (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

        val permissions = packageInfo.requestedPermissions?.toList() ?: emptyList()
        val dangerousPermissions = getDangerousPermissions(permissions)

        val riskScore = calculateRiskScore(
            permissions = permissions,
            dangerousPermissions = dangerousPermissions,
            isSystemApp = isSystemApp
        )

        return AppInfo(
            packageName = packageInfo.packageName,
            appName = appName,
            versionName = packageInfo.versionName ?: "Unknown",
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            },
            installDate = packageInfo.firstInstallTime,
            updateDate = packageInfo.lastUpdateTime,
            isSystemApp = isSystemApp,
            permissions = permissions,
            dangerousPermissions = dangerousPermissions,
            riskScore = riskScore,
            riskLevel = RiskLevel.fromScore(riskScore),
            icon = try {
                applicationInfo.loadIcon(packageManager)
            } catch (e: Exception) {
                null
            }
        )
    }

    private fun getDangerousPermissions(permissions: List<String>): List<String> {
        val dangerousPermissionPrefixes = listOf(
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.READ_CALENDAR",
            "android.permission.WRITE_CALENDAR",
            "android.permission.READ_CALL_LOG",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.SEND_SMS",
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CALL_PHONE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.BODY_SENSORS",
            "android.permission.ACCESS_MEDIA_LOCATION"
        )

        return permissions.filter { permission ->
            dangerousPermissionPrefixes.any { dangerous ->
                permission.contains(dangerous)
            }
        }
    }

    private fun calculateRiskScore(
        permissions: List<String>,
        dangerousPermissions: List<String>,
        isSystemApp: Boolean
    ): Int {
        var score = 0

        // Base score from dangerous permissions (up to 50 points)
        score += (dangerousPermissions.size * 5).coerceAtMost(50)

        // Additional risk factors
        if (permissions.contains("android.permission.INTERNET")) score += 5
        if (permissions.contains("android.permission.ACCESS_NETWORK_STATE")) score += 3
        if (permissions.contains("android.permission.READ_PHONE_STATE")) score += 10
        if (permissions.contains("android.permission.CAMERA")) score += 10
        if (permissions.contains("android.permission.RECORD_AUDIO")) score += 10
        if (permissions.contains("android.permission.ACCESS_FINE_LOCATION")) score += 8
        if (permissions.contains("android.permission.READ_CONTACTS")) score += 8
        if (permissions.contains("android.permission.READ_SMS")) score += 12
        if (permissions.contains("android.permission.SEND_SMS")) score += 12

        // System apps are generally safer
        if (isSystemApp) {
            score = (score * 0.3).toInt()
        }

        // Cap at 100
        return score.coerceIn(0, 100)
    }
}