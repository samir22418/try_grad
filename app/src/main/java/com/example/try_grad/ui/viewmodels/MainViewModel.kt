package com.example.try_grad.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.try_grad.data.local.entities.AppEntity
import com.example.try_grad.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MainUiState(
    val apps: List<AppEntity> = emptyList(),
    val filteredApps: List<AppEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val errorMessage: String? = null,
    val filterType: FilterType = FilterType.ALL,
    val searchQuery: String = "",
    val totalApps: Int = 0,
    val highRiskCount: Int = 0
)

enum class FilterType {
    ALL, USER_APPS, SYSTEM_APPS, HIGH_RISK
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
            _uiState.update { it.copy(isScanning = true, errorMessage = null) }
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

    private fun loadApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val count = repository.getAppCount()
                if (count == 0) {
                    // First time - scan automatically
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

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}