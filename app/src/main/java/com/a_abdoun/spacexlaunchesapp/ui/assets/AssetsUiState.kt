package com.a_abdoun.spacexlaunchesapp.ui.assets

import com.a_abdoun.spacexlaunchesapp.data.model.Rocket

sealed class AssetsUiState {
    object Loading : AssetsUiState()
    data class Success(val rockets: List<Rocket>) : AssetsUiState()
    data class Error(val message: String) : AssetsUiState()
}
