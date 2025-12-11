package com.a_abdoun.spacexlaunchesapp.ui.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository

class LaunchListViewModelFactory(
    private val repository: LaunchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LaunchListViewModel(repository) as T
    }
}
