package com.example.try_grad.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppEntity(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val versionName: String,
    val versionCode: Long,
    val installDate: Long,
    val updateDate: Long,
    val isSystemApp: Boolean,
    val riskScore: Int, // 0-100
    val riskLevel: String, // LOW, MEDIUM, HIGH, CRITICAL
    val dangerousPermissionsCount: Int,
    val normalPermissionsCount: Int,
    val signaturePermissionsCount: Int,
    val allPermissions: String, // JSON array as string
    val scanDate: Long,
    val iconPath: String? = null
)