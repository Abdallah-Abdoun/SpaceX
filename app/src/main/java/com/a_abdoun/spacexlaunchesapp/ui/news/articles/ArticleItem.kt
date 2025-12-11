package com.a_abdoun.spacexlaunchesapp.ui.news.articles

data class ArticleItem(
    val title: String,
    val link: String,
    val pubDate: String,
    val imageUrl: String? = null,
    val source: String
)
