package com.example.try_grad.data.repository

import android.os.Build
import com.example.try_grad.data.local.dao.AppDao
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.data.models.AppInfo
import com.example.try_grad.data.remote.RetrofitClient
import com.example.try_grad.data.remote.models.AppReportDto
import com.example.try_grad.data.remote.models.DeviceReportRequest
import com.example.try_grad.data.remote.models.SyncResponse
import com.example.try_grad.domain.scanner.AppScanner
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

sealed class SyncResult {
    data class Success(val response: SyncResponse) : SyncResult()
    data class Error(val message: String) : SyncResult()
    object NoData : SyncResult()
}

class AppRepository(
    private val appDao: AppDao,
    private val appScanner: AppScanner
) {
    private val gson = Gson()
    private val api = RetrofitClient.apiService

    // ─── Local DB ────────────────────────────────────────────────────────────

    fun getAllApps(): Flow<List<AppEntity>> = appDao.getAllApps()

    fun getUserApps(): Flow<List<AppEntity>> = appDao.getUserApps()

    fun getSystemApps(): Flow<List<AppEntity>> = appDao.getSystemApps()

    fun getHighRiskApps(): Flow<List<AppEntity>> = appDao.getHighRiskApps()

    suspend fun getAppByPackage(packageName: String): AppEntity? {
        return appDao.getAppByPackage(packageName)
    }

    suspend fun scanAndStoreAllApps() {
        val scannedApps = appScanner.scanAllApps()
        val entities = scannedApps.map { it.toEntity() }
        appDao.insertApps(entities)
    }

    suspend fun scanAndStoreSingleApp(packageName: String) {
        val appInfo = appScanner.scanSingleApp(packageName)
        appInfo?.let {
            appDao.insertApp(it.toEntity())
        }
    }

    suspend fun deleteApp(packageName: String) {
        appDao.deleteAppByPackage(packageName)
    }

    suspend fun getAppCount(): Int = appDao.getAppCount()

    suspend fun getHighRiskCount(): Int = appDao.getHighRiskCount()

    // ─── Central Server Sync ─────────────────────────────────────────────────

    /**
     * Uploads all locally-stored scan data to the central server.
     * Call this after [scanAndStoreAllApps] or on demand from the UI.
     *
     * @param deviceId A unique identifier for this device (e.g. Android ID).
     */
    suspend fun syncToServer(deviceId: String): SyncResult {
        return try {
            // 1. Load all apps from local DB
            val apps = mutableListOf<AppEntity>()
            appDao.getAllApps().collect { list ->
                apps.addAll(list)
                return@collect          // take the first emission only
            }

            if (apps.isEmpty()) return SyncResult.NoData

            // 2. Map entities → DTOs
            val appDtos = apps.map { entity ->
                val permissionsList: List<String> = try {
                    gson.fromJson(entity.allPermissions, Array<String>::class.java).toList()
                } catch (e: Exception) {
                    emptyList()
                }
                AppReportDto(
                    packageName = entity.packageName,
                    appName = entity.appName,
                    versionName = entity.versionName,
                    versionCode = entity.versionCode,
                    installDate = entity.installDate,
                    updateDate = entity.updateDate,
                    isSystemApp = entity.isSystemApp,
                    riskScore = entity.riskScore,
                    riskLevel = entity.riskLevel,
                    dangerousPermissionsCount = entity.dangerousPermissionsCount,
                    normalPermissionsCount = entity.normalPermissionsCount,
                    allPermissions = permissionsList,
                    scanDate = entity.scanDate
                )
            }

            // 3. Build the device report
            val request = DeviceReportRequest(
                deviceId = deviceId,
                deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
                androidVersion = Build.VERSION.RELEASE,
                scanTimestamp = System.currentTimeMillis(),
                apps = appDtos
            )

            // 4. POST to server
            val response = api.uploadReport(request)
            if (response.isSuccessful && response.body() != null) {
                SyncResult.Success(response.body()!!)
            } else {
                SyncResult.Error("Server error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            SyncResult.Error(e.message ?: "Unknown network error")
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private fun AppInfo.toEntity(): AppEntity {
        return AppEntity(
            packageName = packageName,
            appName = appName,
            versionName = versionName,
            versionCode = versionCode,
            installDate = installDate,
            updateDate = updateDate,
            isSystemApp = isSystemApp,
            riskScore = riskScore,
            riskLevel = riskLevel.name,
            isTrusted = isTrusted,
            dangerousPermissionsCount = dangerousPermissions.size,
            normalPermissionsCount = permissions.size - dangerousPermissions.size,
            signaturePermissionsCount = 0,
            allPermissions = gson.toJson(permissions),
            scanDate = System.currentTimeMillis()
        )
    }
}