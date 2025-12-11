package com.a_abdoun.spacexlaunchesapp.ui.launchdetail

sealed class LaunchDetailUiState<out T> {
    object Loading : LaunchDetailUiState<Nothing>()
    data class Success<T>(val data: T) : LaunchDetailUiState<T>()
    data class Error(val message: String) : LaunchDetailUiState<Nothing>()
}
