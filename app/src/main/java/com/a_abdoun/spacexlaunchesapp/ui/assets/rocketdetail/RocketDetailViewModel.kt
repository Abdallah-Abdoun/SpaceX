package com.a_abdoun.spacexlaunchesapp.ui.assets.rocketdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.model.Rocket
import com.a_abdoun.spacexlaunchesapp.data.network.SpaceXApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RocketDetailUiState {
    object Loading : RocketDetailUiState()
    data class Success(val rocket: Rocket) : RocketDetailUiState()
    data class Error(val message: String) : RocketDetailUiState()
}

class RocketDetailViewModel(
    private val api: SpaceXApiService,
    private val rocketId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<RocketDetailUiState>(RocketDetailUiState.Loading)
    val uiState: StateFlow<RocketDetailUiState> = _uiState

    init {
        loadRocket()
    }

    private fun loadRocket() {
        viewModelScope.launch {
            try {
                val rocket = api.getRocketById(rocketId)
                _uiState.value = RocketDetailUiState.Success(rocket)
            } catch (e: Exception) {
                _uiState.value = RocketDetailUiState.Error(e.localizedMessage ?: "Fehler beim Laden")
            }
        }
    }
}

class RocketDetailViewModelFactory(
    private val api: SpaceXApiService,
    private val rocketId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RocketDetailViewModel(api, rocketId) as T
    }
}
