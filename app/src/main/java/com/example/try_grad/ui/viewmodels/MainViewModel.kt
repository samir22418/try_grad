package com.example.try_grad.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.data.repository.AppRepository
import com.example.try_grad.data.repository.SyncResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MainUiState(
    val apps: List<AppEntity> = emptyList(),
    val filteredApps: List<AppEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val isSyncing: Boolean = false,
    val syncStatus: SyncStatus = SyncStatus.Idle,
    val errorMessage: String? = null,
    val filterType: FilterType = FilterType.ALL,
    val searchQuery: String = "",
    val totalApps: Int = 0,
    val highRiskCount: Int = 0
)

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Syncing : SyncStatus()
    data class Success(val reportId: String?, val totalApps: Int) : SyncStatus()
    data class Error(val message: String) : SyncStatus()
    object NoData : SyncStatus()
}

enum class FilterType(val label: String) {
    ALL("All Apps"),
    USER_APPS("User Apps"),
    SYSTEM_APPS("System Apps"),
    HIGH_RISK("High Risk")
}

class MainViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadApps()
        observeApps()
    }

    private fun observeApps() {
        viewModelScope.launch {
            repository.getAllApps().collectLatest { apps ->
                _uiState.update { state ->
                    state.copy(
                        apps = apps,
                        filteredApps = filterApps(apps, state.filterType, state.searchQuery),
                        totalApps = apps.size,
                        highRiskCount = apps.count { it.riskLevel == "HIGH" || it.riskLevel == "CRITICAL" }
                    )
                }
            }
        }
    }

    fun startScan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, errorMessage = null, syncStatus = SyncStatus.Idle) }
            try {
                repository.scanAndStoreAllApps()
                _uiState.update { it.copy(isScanning = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isScanning = false,
                        errorMessage = "Scan failed: ${e.message}"
                    )
                }
            }
        }
    }

    @SuppressLint("HardwareIds")
    fun syncToServer(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, syncStatus = SyncStatus.Syncing) }
            val deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "unknown_device"

            when (val result = repository.syncToServer(deviceId)) {
                is SyncResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            syncStatus = SyncStatus.Success(
                                reportId = result.response.reportId,
                                totalApps = result.response.totalApps
                            )
                        )
                    }
                }
                is SyncResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            syncStatus = SyncStatus.Error(result.message)
                        )
                    }
                }
                is SyncResult.NoData -> {
                    _uiState.update {
                        it.copy(isSyncing = false, syncStatus = SyncStatus.NoData)
                    }
                }
            }
        }
    }

    fun setFilter(filterType: FilterType) {
        _uiState.update { state ->
            state.copy(
                filterType = filterType,
                filteredApps = filterApps(state.apps, filterType, state.searchQuery)
            )
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredApps = filterApps(state.apps, state.filterType, query)
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearSyncStatus() {
        _uiState.update { it.copy(syncStatus = SyncStatus.Idle) }
    }

    private fun loadApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val count = repository.getAppCount()
                if (count == 0) {
                    startScan()
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load apps: ${e.message}"
                    )
                }
            }
        }
    }

    private fun filterApps(
        apps: List<AppEntity>,
        filterType: FilterType,
        searchQuery: String
    ): List<AppEntity> {
        var filtered = when (filterType) {
            FilterType.ALL -> apps
            FilterType.USER_APPS -> apps.filter { !it.isSystemApp }
            FilterType.SYSTEM_APPS -> apps.filter { it.isSystemApp }
            FilterType.HIGH_RISK -> apps.filter { it.riskLevel == "HIGH" || it.riskLevel == "CRITICAL" }
        }

        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.appName.contains(searchQuery, ignoreCase = true) ||
                        it.packageName.contains(searchQuery, ignoreCase = true)
            }
        }

        return filtered
    }
}