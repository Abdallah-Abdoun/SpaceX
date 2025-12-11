package com.a_abdoun.spacexlaunchesapp.ui.news

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a_abdoun.spacexlaunchesapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val launches = RetrofitInstance.api.getAllLaunches()
                val articles = launches
                    .filter { it.links.article != null }
                    .map {
                        val articleUrl = it.links.article!!
                        val source = Uri.parse(articleUrl).host ?: "SpaceX"

                        NewsItem(
                            title = it.name,
                            imageUrl = it.links.flickr.original.firstOrNull() ?: it.links.patch.small,
                            articleUrl = articleUrl,
                            source = source,
                            publishedDate = it.date_utc.take(10)
                        )
                    }
                _uiState.value = NewsUiState.Success(
                    articles = articles,
                    launches = launches
                )

            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.localizedMessage ?: "Fehler beim Laden")
            }
        }
    }

}
