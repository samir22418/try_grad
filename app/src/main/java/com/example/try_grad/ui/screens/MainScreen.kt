package com.example.try_grad.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.ui.viewmodels.FilterType
import com.example.try_grad.ui.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    hasUsageStatsPermission: Boolean,
    onRequestUsageStats: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security Scanner") },
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, "Filter")
                    }
                    IconButton(onClick = { viewModel.startScan() }) {
                        Icon(
                            if (uiState.isScanning) Icons.Default.HourglassEmpty
                            else Icons.Default.Refresh,
                            "Scan"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Permission Warning
            if (!hasUsageStatsPermission) {
                PermissionWarningBanner(onRequestUsageStats)
            }

            // Stats Cards
            StatsSection(
                totalApps = uiState.totalApps,
                highRiskCount = uiState.highRiskCount
            )

            // Search Bar
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) }
            )

            // Filter Menu
            FilterMenu(
                expanded = showFilterMenu,
                currentFilter = uiState.filterType,
                onDismiss = { showFilterMenu = false },
                onFilterSelected = {
                    viewModel.setFilter(it)
                    showFilterMenu = false
                }
            )

            // App List
            when {
                uiState.isScanning -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Scanning apps...")
                        }
                    }
                }
                uiState.filteredApps.isEmpty() -> {
                    EmptyState(uiState.searchQuery.isNotBlank())
                }
                else -> {
                    AppList(apps = uiState.filteredApps)
                }
            }
        }
    }
}

@Composable
fun PermissionWarningBanner(onRequestUsageStats: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Usage Stats Permission Required",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Enable for real-time monitoring",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onRequestUsageStats) {
                Text("Grant")
            }
        }
    }
}

@Composable
fun StatsSection(totalApps: Int, highRiskCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            title = "Total Apps",
            value = totalApps.toString(),
            icon = Icons.Default.Apps,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "High Risk",
            value = highRiskCount.toString(),
            icon = Icons.Default.Warning,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                title,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = { Text("Search apps...") },
        leadingIcon = { Icon(Icons.Default.Search, "Search") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, "Clear")
                }
            }
        },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun FilterMenu(
    expanded: Boolean,
    currentFilter: FilterType,
    onDismiss: () -> Unit,
    onFilterSelected: (FilterType) -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        FilterType.entries.forEach { filter ->
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (currentFilter == filter) {
                            Icon(Icons.Default.Check, null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Spacer(modifier = Modifier.width(28.dp))
                        }
                        Text(filter.name.replace("_", " "))
                    }
                },
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
fun AppList(apps: List<AppEntity>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(apps, key = { it.packageName }) { app ->
            AppListItem(app)
        }
    }
}

@Composable
fun AppListItem(app: AppEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Icon Placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Android,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // App Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    app.appName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    app.packageName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (app.isSystemApp) {
                        Chip(text = "System", color = MaterialTheme.colorScheme.secondary)
                    }
                    Chip(
                        text = "${app.dangerousPermissionsCount} Dangerous",
                        color = if (app.dangerousPermissionsCount > 5)
                            MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Risk Score
            RiskScoreBadge(
                score = app.riskScore,
                level = app.riskLevel
            )
        }
    }
}

@Composable
fun Chip(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.2f)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun RiskScoreBadge(score: Int, level: String) {
    val color = when (level) {
        "LOW" -> Color(0xFF4CAF50)
        "MEDIUM" -> Color(0xFFFFC107)
        "HIGH" -> Color(0xFFFF9800)
        "CRITICAL" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                score.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Text(
            level,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun EmptyState(isSearching: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                if (isSearching) Icons.Default.SearchOff else Icons.Default.Inbox,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                if (isSearching) "No apps found" else "No apps installed",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}