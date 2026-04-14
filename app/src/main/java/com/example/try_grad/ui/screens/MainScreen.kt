package com.example.try_grad.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.ui.theme.*
import com.example.try_grad.ui.viewmodels.FilterType
import com.example.try_grad.ui.viewmodels.MainViewModel
import com.example.try_grad.ui.viewmodels.SyncStatus

// ──────────────────────────────────────────────────────────────────────────────
// Root Screen
// ──────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    hasUsageStatsPermission: Boolean,
    onRequestUsageStats: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var activeFilter by remember { mutableStateOf(FilterType.ALL) }

    // Sync result snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.syncStatus) {
        when (val s = uiState.syncStatus) {
            is SyncStatus.Success ->
                snackbarHostState.showSnackbar("✅ Synced ${s.totalApps} apps to server")
            is SyncStatus.Error ->
                snackbarHostState.showSnackbar("❌ Sync failed: ${s.message}")
            is SyncStatus.NoData ->
                snackbarHostState.showSnackbar("⚠️ No data to sync. Run a scan first.")
            else -> {}
        }
        if (uiState.syncStatus !is SyncStatus.Idle && uiState.syncStatus !is SyncStatus.Syncing) {
            viewModel.clearSyncStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(CyberBlue, CyberBlueDark)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Shield,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "SecureGuard",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                "Device Security Scanner",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                actions = {
                    // Sync button
                    IconButton(
                        onClick = { viewModel.syncToServer(context) },
                        enabled = !uiState.isSyncing && !uiState.isScanning
                    ) {
                        if (uiState.isSyncing) {
                            val rotation by rememberInfiniteTransition(label = "spin").animateFloat(
                                0f, 360f,
                                animationSpec = infiniteRepeatable(
                                    tween(1000, easing = LinearEasing)
                                ),
                                label = "sync_spin"
                            )
                            Icon(
                                Icons.Default.Sync,
                                contentDescription = "Syncing",
                                tint = CyberBlue,
                                modifier = Modifier.graphicsLayer { rotationZ = rotation }
                            )
                        } else {
                            Icon(
                                Icons.Default.CloudUpload,
                                contentDescription = "Sync to Server",
                                tint = CyberBlue
                            )
                        }
                    }
                    // Scan button
                    IconButton(
                        onClick = { viewModel.startScan() },
                        enabled = !uiState.isScanning && !uiState.isSyncing
                    ) {
                        if (uiState.isScanning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = NeonTeal
                            )
                        } else {
                            Icon(
                                Icons.Default.DocumentScanner,
                                contentDescription = "Scan",
                                tint = NeonTeal
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSurface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // ── Permission Warning ──────────────────────────────────────────
            if (!hasUsageStatsPermission) {
                item {
                    PermissionBanner(onRequestUsageStats)
                }
            }

            // ── Dashboard Header ────────────────────────────────────────────
            item {
                DashboardHeader(
                    totalApps = uiState.totalApps,
                    highRiskCount = uiState.highRiskCount,
                    apps = uiState.apps
                )
            }

            // ── Sync Status Card ────────────────────────────────────────────
            item {
                SyncStatusCard(
                    isSyncing = uiState.isSyncing,
                    syncStatus = uiState.syncStatus,
                    onSync = { viewModel.syncToServer(context) }
                )
            }

            // ── Search Bar ──────────────────────────────────────────────────
            item {
                SearchField(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.setSearchQuery(it) }
                )
            }

            // ── Filter Chips ────────────────────────────────────────────────
            item {
                FilterChipsRow(
                    currentFilter = activeFilter,
                    onFilterSelected = {
                        activeFilter = it
                        viewModel.setFilter(it)
                    },
                    apps = uiState.apps
                )
            }

            // ── Section Label ───────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${uiState.filteredApps.size} Apps",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        "sorted by risk",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }

            // ── App List ────────────────────────────────────────────────────
            when {
                uiState.isScanning -> {
                    item { ScanningPlaceholder() }
                }
                uiState.filteredApps.isEmpty() -> {
                    item { EmptyStateCard(isSearching = uiState.searchQuery.isNotBlank()) }
                }
                else -> {
                    items(uiState.filteredApps, key = { it.packageName }) { app ->
                        AppCard(app = app)
                    }
                }
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Dashboard Header
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun DashboardHeader(totalApps: Int, highRiskCount: Int, apps: List<AppEntity>) {
    val safeApps  = apps.count { it.riskLevel == "LOW" }
    val medApps   = apps.count { it.riskLevel == "MEDIUM" }
    val highApps  = apps.count { it.riskLevel == "HIGH" }
    val critApps  = apps.count { it.riskLevel == "CRITICAL" }

    val overallScore = if (apps.isEmpty()) 100
    else (100 - (apps.sumOf { it.riskScore } / apps.size.coerceAtLeast(1))).coerceIn(0, 100)

    Column {
        // ── Gradient hero card ──────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0D1B3E), DarkBg)
                    )
                )
        ) {
            // Subtle grid lines for cyber effect
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 40f
                var x = 0f
                while (x < size.width) {
                    drawLine(
                        color = Color(0x0800B4FF),
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = 1f
                    )
                    x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(
                        color = Color(0x0800B4FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1f
                    )
                    y += step
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Security Score Ring
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(130.dp)) {
                    SecurityScoreRing(score = overallScore)
                }

                Spacer(Modifier.width(20.dp))

                // Stats column
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniStat("Total Apps", totalApps.toString(), CyberBlue)
                    MiniStat("High/Critical", highRiskCount.toString(), RiskCritical)
                    MiniStat("Safe Apps", safeApps.toString(), RiskLow)
                }
            }
        }

        // ── Risk breakdown bar ──────────────────────────────────────────────
        if (apps.isNotEmpty()) {
            RiskBreakdownBar(
                safe = safeApps,
                medium = medApps,
                high = highApps,
                critical = critApps,
                total = apps.size
            )
        }
    }
}

@Composable
fun SecurityScoreRing(score: Int) {
    val animScore by animateFloatAsState(
        targetValue = score.toFloat(),
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = "score"
    )
    val color = when {
        score >= 80 -> RiskLow
        score >= 60 -> RiskMedium
        score >= 40 -> RiskHigh
        else        -> RiskCritical
    }
    val label = when {
        score >= 80 -> "Secure"
        score >= 60 -> "Fair"
        score >= 40 -> "At Risk"
        else        -> "Critical"
    }

    Canvas(modifier = Modifier.size(130.dp)) {
        val stroke = Stroke(width = 12f, cap = StrokeCap.Round)
        val sweepAngle = (animScore / 100f) * 270f
        drawArc(
            color = Color(0xFF1C2333),
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = stroke
        )
        drawArc(
            brush = Brush.sweepGradient(
                listOf(color.copy(alpha = 0.6f), color)
            ),
            startAngle = 135f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "${score}",
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )
        Text(
            label,
            color = color.copy(alpha = 0.8f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            "Security Score",
            color = TextMuted,
            fontSize = 10.sp
        )
    }
}

@Composable
fun MiniStat(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(value, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(label, color = TextSecondary, fontSize = 11.sp)
        }
    }
}

@Composable
fun RiskBreakdownBar(safe: Int, medium: Int, high: Int, critical: Int, total: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text("Risk Distribution", color = TextSecondary, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            val segments = listOf(
                safe to RiskLow,
                medium to RiskMedium,
                high to RiskHigh,
                critical to RiskCritical
            )
            segments.forEach { (count, color) ->
                if (count > 0) {
                    Box(
                        Modifier
                            .weight(count.toFloat())
                            .fillMaxHeight()
                            .background(color)
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            RiskLegendItem("Low", safe, RiskLow)
            RiskLegendItem("Medium", medium, RiskMedium)
            RiskLegendItem("High", high, RiskHigh)
            RiskLegendItem("Critical", critical, RiskCritical)
        }
    }
}

@Composable
fun RiskLegendItem(label: String, count: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            "$label ($count)",
            color = TextSecondary,
            fontSize = 11.sp
        )
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Sync Status Card
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun SyncStatusCard(
    isSyncing: Boolean,
    syncStatus: SyncStatus,
    onSync: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, Divider)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon column
            Box(
                Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GlowBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = CyberBlue,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    "Central Server Sync",
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                val statusText = when (syncStatus) {
                    is SyncStatus.Idle    -> "Upload raw scan data to your server"
                    is SyncStatus.Syncing -> "Uploading data…"
                    is SyncStatus.Success -> "Last sync: ${syncStatus.totalApps} apps"
                    is SyncStatus.Error   -> "Failed — tap to retry"
                    is SyncStatus.NoData  -> "No data yet — scan first"
                }
                val statusColor = when (syncStatus) {
                    is SyncStatus.Error -> RiskCritical
                    is SyncStatus.Success -> RiskLow
                    else -> TextSecondary
                }
                Text(statusText, color = statusColor, fontSize = 12.sp)
            }

            Button(
                onClick = onSync,
                enabled = !isSyncing,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberBlue,
                    disabledContainerColor = DarkSurfaceVar
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    if (isSyncing) "Syncing…" else "Sync",
                    color = if (isSyncing) TextSecondary else DarkBg,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Search Field
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search apps or package name…", color = TextMuted) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary)
        },
        trailingIcon = {
            AnimatedVisibility(visible = query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                }
            }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CyberBlue,
            unfocusedBorderColor = Divider,
            focusedContainerColor = DarkSurface,
            unfocusedContainerColor = DarkSurface,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = CyberBlue
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

// ──────────────────────────────────────────────────────────────────────────────
// Filter Chips
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun FilterChipsRow(
    currentFilter: FilterType,
    onFilterSelected: (FilterType) -> Unit,
    apps: List<AppEntity>
) {
    val counts = mapOf(
        FilterType.ALL        to apps.size,
        FilterType.USER_APPS  to apps.count { !it.isSystemApp },
        FilterType.SYSTEM_APPS to apps.count { it.isSystemApp },
        FilterType.HIGH_RISK  to apps.count { it.riskLevel == "HIGH" || it.riskLevel == "CRITICAL" }
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(FilterType.entries) { filter ->
            val selected = filter == currentFilter
            val count = counts[filter] ?: 0
            FilterChip(
                selected = selected,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        "${filter.label} ($count)",
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = DarkSurface,
                    labelColor = TextSecondary,
                    selectedContainerColor = CyberBlue.copy(alpha = 0.15f),
                    selectedLabelColor = CyberBlue
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Divider,
                    selectedBorderColor = CyberBlue,
                    enabled = true,
                    selected = selected
                )
            )
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// App Card
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun AppCard(app: AppEntity) {
    val riskColor = riskColor(app.riskLevel)
    val riskBg    = riskBgColor(app.riskLevel)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, Divider),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App icon placeholder with glow
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(riskBg, DarkSurface2)
                        )
                    )
                    .border(1.dp, riskColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    app.appName.take(1).uppercase(),
                    color = riskColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.width(14.dp))

            // Info
            Column(Modifier.weight(1f)) {
                Text(
                    app.appName,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    app.packageName,
                    color = TextMuted,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (app.isSystemApp) {
                        MicroChip("System", DarkSurfaceVar, TextSecondary)
                    }
                    when {
                        app.isTrusted -> MicroChip("✓ Trusted", Color(0xFF0D2A28), NeonTeal)
                        app.dangerousPermissionsCount > 0 -> MicroChip(
                            "⚠ ${app.dangerousPermissionsCount} dangerous",
                            riskBg,
                            riskColor
                        )
                        else -> MicroChip("✓ Clean", RiskLowBg, RiskLow)
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            // Risk Score Circle
            RiskScoreCircle(score = app.riskScore, level = app.riskLevel)
        }

        // Inline risk progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(DarkSurface2)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(app.riskScore / 100f)
                    .fillMaxHeight()
                    .background(
                        Brush.horizontalGradient(
                            listOf(riskColor.copy(alpha = 0.5f), riskColor)
                        )
                    )
            )
        }
    }
}

@Composable
fun MicroChip(text: String, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(text, color = textColor, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun RiskScoreCircle(score: Int, level: String) {
    val color = riskColor(level)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(riskBgColor(level))
                .border(1.5.dp, color.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                score.toString(),
                color = color,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            level,
            color = color.copy(alpha = 0.8f),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Permission Banner
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun PermissionBanner(onRequest: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = RiskCriticalBg),
        border = BorderStroke(1.dp, RiskCritical.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = RiskCritical,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Usage Stats Required",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    "Needed for real-time monitoring",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
            TextButton(
                onClick = onRequest,
                colors = ButtonDefaults.textButtonColors(contentColor = RiskCritical)
            ) {
                Text("Grant", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Scanning Placeholder
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun ScanningPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
            label = "pulse_alpha"
        )
        Icon(
            Icons.Default.Shield,
            contentDescription = null,
            tint = CyberBlue.copy(alpha = pulse),
            modifier = Modifier.size(64.dp)
        )
        Text("Scanning device…", color = CyberBlue, fontWeight = FontWeight.SemiBold)
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(0.5f),
            color = CyberBlue,
            trackColor = DarkSurface2
        )
        Text("Analysing permissions and risk levels", color = TextMuted, fontSize = 12.sp)
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Empty State
// ──────────────────────────────────────────────────────────────────────────────

@Composable
fun EmptyStateCard(isSearching: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            if (isSearching) Icons.Default.SearchOff else Icons.Default.Inbox,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(56.dp)
        )
        Text(
            if (isSearching) "No apps match your search" else "No apps found",
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
        Text(
            if (isSearching) "Try a different keyword" else "Tap the scan button to start",
            color = TextMuted,
            fontSize = 13.sp
        )
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Helpers
// ──────────────────────────────────────────────────────────────────────────────

fun riskColor(level: String): Color = when (level) {
    "LOW"      -> RiskLow
    "MEDIUM"   -> RiskMedium
    "HIGH"     -> RiskHigh
    "CRITICAL" -> RiskCritical
    else       -> Color.Gray
}

fun riskBgColor(level: String): Color = when (level) {
    "LOW"      -> RiskLowBg
    "MEDIUM"   -> RiskMediumBg
    "HIGH"     -> RiskHighBg
    "CRITICAL" -> RiskCriticalBg
    else       -> Color(0xFF1C2333)
}