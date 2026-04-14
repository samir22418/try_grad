package com.example.try_grad.data.models

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val versionName: String,
    val versionCode: Long,
    val installDate: Long,
    val updateDate: Long,
    val isSystemApp: Boolean,
    val permissions: List<String>,
    val dangerousPermissions: List<String>,
    val riskScore: Int,
    val riskLevel: RiskLevel,
    /** True if the app matched the TrustedAppsWhitelist — score is capped regardless of permissions. */
    val isTrusted: Boolean = false,
    val icon: Drawable? = null
)

enum class RiskLevel(val displayName: String, val color: Long) {
    LOW("Low", 0xFF4CAF50),
    MEDIUM("Medium", 0xFFFFC107),
    HIGH("High", 0xFFFF9800),
    CRITICAL("Critical", 0xFFF44336);

    companion object {
        fun fromScore(score: Int): RiskLevel {
            return when {
                score < 30 -> LOW
                score < 60 -> MEDIUM
                score < 80 -> HIGH
                else -> CRITICAL
            }
        }
    }
}

data class PermissionInfo(
    val name: String,
    val protectionLevel: String,
    val isDangerous: Boolean,
    val description: String? = null
)