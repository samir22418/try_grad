package com.example.try_grad.data.local.dao

import androidx.room.*
import com.example.try_grad.data.local.entities.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM apps ORDER BY riskScore DESC, appName ASC")
    fun getAllApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE isSystemApp = 0 ORDER BY riskScore DESC, appName ASC")
    fun getUserApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE isSystemApp = 1 ORDER BY appName ASC")
    fun getSystemApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE packageName = :packageName")
    suspend fun getAppByPackage(packageName: String): AppEntity?

    @Query("SELECT * FROM apps WHERE riskLevel = :level ORDER BY riskScore DESC")
    fun getAppsByRiskLevel(level: String): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE riskScore >= :minScore ORDER BY riskScore DESC")
    fun getHighRiskApps(minScore: Int = 70): Flow<List<AppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: AppEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApps(apps: List<AppEntity>)

    @Update
    suspend fun updateApp(app: AppEntity)

    @Delete
    suspend fun deleteApp(app: AppEntity)

    @Query("DELETE FROM apps WHERE packageName = :packageName")
    suspend fun deleteAppByPackage(packageName: String)

    @Query("DELETE FROM apps")
    suspend fun deleteAllApps()

    @Query("SELECT COUNT(*) FROM apps")
    suspend fun getAppCount(): Int

    @Query("SELECT COUNT(*) FROM apps WHERE riskLevel = 'HIGH' OR riskLevel = 'CRITICAL'")
    suspend fun getHighRiskCount(): Int
}