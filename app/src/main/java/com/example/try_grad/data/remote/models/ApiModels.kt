package com.example.try_grad.data.remote.models

import com.google.gson.annotations.SerializedName

/**
 * Full report sent to the central server after a device scan.
 */
data class DeviceReportRequest(
    @SerializedName("device_id")
    val deviceId: String,

    @SerializedName("device_model")
    val deviceModel: String,

    @SerializedName("android_version")
    val androidVersion: String,

    @SerializedName("scan_timestamp")
    val scanTimestamp: Long,

    @SerializedName("apps")
    val apps: List<AppReportDto>
)

/**
 * Per-app data sent to the server.
 */
data class AppReportDto(
    @SerializedName("package_name")
    val packageName: String,

    @SerializedName("app_name")
    val appName: String,

    @SerializedName("version_name")
    val versionName: String,

    @SerializedName("version_code")
    val versionCode: Long,

    @SerializedName("install_date")
    val installDate: Long,

    @SerializedName("update_date")
    val updateDate: Long,

    @SerializedName("is_system_app")
    val isSystemApp: Boolean,

    @SerializedName("risk_score")
    val riskScore: Int,

    @SerializedName("risk_level")
    val riskLevel: String,

    @SerializedName("dangerous_permissions_count")
    val dangerousPermissionsCount: Int,

    @SerializedName("normal_permissions_count")
    val normalPermissionsCount: Int,

    @SerializedName("all_permissions")
    val allPermissions: List<String>,

    @SerializedName("scan_date")
    val scanDate: Long
)

/**
 * Server response after receiving the report.
 */
data class SyncResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("report_id")
    val reportId: String? = null,

    @SerializedName("total_apps")
    val totalApps: Int = 0,

    @SerializedName("high_risk_count")
    val highRiskCount: Int = 0,

    @SerializedName("server_timestamp")
    val serverTimestamp: Long = 0L
)
