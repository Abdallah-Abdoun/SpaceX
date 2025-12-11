package com.a_abdoun.spacexlaunchesapp.ui.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.local.toLaunch
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LaunchListViewModel(
    private val repository: LaunchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LaunchListUiState>(LaunchListUiState.Loading)
    val uiState: StateFlow<LaunchListUiState> = _uiState

    init {
        loadLaunches()
    }

    private fun loadLaunches() {
        viewModelScope.launch {
            try {
                repository.getAllLaunches().collect { entityList ->
                    val launches = entityList.mapNotNull { it.toLaunch() }
                    _uiState.value = LaunchListUiState.Success(launches)

                    if (launches.isNotEmpty()) {
                        println("✅ First Launch: ${launches[0]}")
                    } else {
                        println("⚠️ No launches found.")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = LaunchListUiState.Error(e.message ?: "Ein Fehler ist aufgetreten")
            }
        }
    }

}
