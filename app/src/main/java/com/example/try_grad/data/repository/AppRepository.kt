package com.example.try_grad.data.repository

import com.example.try_grad.data.local.dao.AppDao
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.data.models.AppInfo
import com.example.try_grad.domain.scanner.AppScanner
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val appDao: AppDao,
    private val appScanner: AppScanner
) {
    private val gson = Gson()

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
            dangerousPermissionsCount = dangerousPermissions.size,
            normalPermissionsCount = permissions.size - dangerousPermissions.size,
            signaturePermissionsCount = 0,
            allPermissions = gson.toJson(permissions),
            scanDate = System.currentTimeMillis()
        )
    }
}