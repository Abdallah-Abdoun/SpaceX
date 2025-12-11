package com.a_abdoun.spacexlaunchesapp.ui.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.model.Rocket
import com.a_abdoun.spacexlaunchesapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssetsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<AssetsUiState>(AssetsUiState.Loading)
    val uiState: StateFlow<AssetsUiState> = _uiState

    init {
        fetchRockets()
    }

    private fun fetchRockets() {
        viewModelScope.launch {
            try {
                val rockets = RetrofitInstance.api.getAllRockets()
                _uiState.value = AssetsUiState.Success(rockets)
            } catch (e: Exception) {
                _uiState.value = AssetsUiState.Error(e.localizedMessage ?: "Fehler beim Laden")
            }
        }
    }
}
