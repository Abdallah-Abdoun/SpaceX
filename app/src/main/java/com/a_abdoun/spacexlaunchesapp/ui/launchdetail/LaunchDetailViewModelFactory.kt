package com.a_abdoun.spacexlaunchesapp.ui.launchdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository

class LaunchDetailViewModelFactory(
    private val repository: LaunchRepository,
    private val launchId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LaunchDetailViewModel(repository, launchId) as T
    }
}
