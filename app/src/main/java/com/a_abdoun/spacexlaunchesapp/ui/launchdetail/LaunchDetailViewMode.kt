package com.a_abdoun.spacexlaunchesapp.ui.launchdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.local.toLaunch
import com.a_abdoun.spacexlaunchesapp.data.model.Launch
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LaunchDetailViewModel(
    private val repository: LaunchRepository,
    private val launchId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<LaunchDetailUiState<Launch>>(LaunchDetailUiState.Loading)
    val uiState: StateFlow<LaunchDetailUiState<Launch>> = _uiState

    init {
        fetchLaunchDetails()
    }

    private fun fetchLaunchDetails() {
        viewModelScope.launch {
            try {
                _uiState.value = LaunchDetailUiState.Loading

                repository.getLaunchById(launchId).collect { entity ->
                    val launch = entity?.toLaunch()
                    if (launch != null) {
                        _uiState.value = LaunchDetailUiState.Success(launch)
                    } else {
                        _uiState.value = LaunchDetailUiState.Error("Launch nicht gefunden.")
                    }
                }

            } catch (e: Exception) {
                _uiState.value = LaunchDetailUiState.Error("Fehler beim Laden: ${e.message}")
            }
        }
    }

}
