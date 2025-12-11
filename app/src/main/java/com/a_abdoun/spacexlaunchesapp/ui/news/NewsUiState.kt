package com.a_abdoun.spacexlaunchesapp.ui.news

import com.a_abdoun.spacexlaunchesapp.data.model.Launch

data class NewsItem(
    val title: String,
    val imageUrl: String?,
    val articleUrl: String,
    val source: String,
    val publishedDate: String
)


sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(
        val articles: List<NewsItem>,
        val launches: List<com.a_abdoun.spacexlaunchesapp.data.model.Launch>
    ) : NewsUiState()

    data class Error(val message: String) : NewsUiState()
}
