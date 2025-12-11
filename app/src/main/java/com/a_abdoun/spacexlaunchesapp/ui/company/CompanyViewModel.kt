package com.a_abdoun.spacexlaunchesapp.ui.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.model.CompanyInfo
import com.a_abdoun.spacexlaunchesapp.data.network.SpaceXApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CompanyUiState {
    object Loading : CompanyUiState()
    data class Success(val company: CompanyInfo) : CompanyUiState()
    data class Error(val message: String) : CompanyUiState()
}

class CompanyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CompanyUiState>(CompanyUiState.Loading)
    val uiState: StateFlow<CompanyUiState> = _uiState

    private val api = SpaceXApiService.create()

    init {
        fetchCompanyInfo()
    }

    private fun fetchCompanyInfo() {
        viewModelScope.launch {
            try {
                val result = api.getCompanyInfo()
                _uiState.value = CompanyUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = CompanyUiState.Error(e.localizedMessage ?: "Failed to load company info")
            }
        }
    }
}
