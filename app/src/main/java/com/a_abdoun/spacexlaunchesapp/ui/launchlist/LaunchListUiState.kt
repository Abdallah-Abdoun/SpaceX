package com.a_abdoun.spacexlaunchesapp.ui.launchlist

import com.a_abdoun.spacexlaunchesapp.data.model.Launch

sealed class LaunchListUiState {
    object Loading : LaunchListUiState()
    data class Success(val launches: List<Launch>) : LaunchListUiState()
    data class Error(val message: String) : LaunchListUiState()
}
