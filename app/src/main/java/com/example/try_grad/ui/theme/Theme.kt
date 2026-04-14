package com.example.try_grad.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AppDarkColorScheme = darkColorScheme(
    primary          = CyberBlue,
    onPrimary        = DarkBg,
    primaryContainer = DarkSurfaceVar,
    onPrimaryContainer = CyberBlueLight,

    secondary        = NeonTeal,
    onSecondary      = DarkBg,
    secondaryContainer = Color(0xFF0D2A28),
    onSecondaryContainer = NeonTeal,

    tertiary         = Color(0xFF7C6FFF),
    onTertiary       = DarkBg,

    background       = DarkBg,
    onBackground     = TextPrimary,

    surface          = DarkSurface,
    onSurface        = TextPrimary,
    surfaceVariant   = DarkSurfaceVar,
    onSurfaceVariant = TextSecondary,

    error            = RiskCritical,
    onError          = TextPrimary,
    errorContainer   = RiskCriticalBg,
    onErrorContainer = RiskCritical,

    outline          = Divider,
    outlineVariant   = DarkSurface2,
)

// Always force dark theme for a consistent security-app feel
@Composable
fun Try_gradTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = AppDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkBg.toArgb()
            window.navigationBarColor = DarkBg.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}