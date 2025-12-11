package com.a_abdoun.spacexlaunchesapp.ui.news.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

sealed class ArticlesUiState {
    object Loading : ArticlesUiState()
    data class Success(val articles: List<ArticleItem>) : ArticlesUiState()
    data class Error(val message: String) : ArticlesUiState()
}

class ArticlesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ArticlesUiState>(ArticlesUiState.Loading)
    val uiState: StateFlow<ArticlesUiState> = _uiState

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articles = parseRss("https://www.nasaspaceflight.com/feed/")
                _uiState.value = ArticlesUiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = ArticlesUiState.Error(e.message ?: "Parsing error")
            }
        }
    }

    private fun parseRss(urlString: String): List<ArticleItem> {
        val articles = mutableListOf<ArticleItem>()

        val url = URL(urlString)
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(url.openStream(), "UTF_8")

        var eventType = parser.eventType
        var insideItem = false
        var title: String? = null
        var link: String? = null
        var pubDate: String? = null
        var imageUrl: String? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when {
                        tagName.equals("item", ignoreCase = true) -> insideItem = true
                        insideItem && tagName.equals("title", ignoreCase = true) -> title = parser.nextText()
                        insideItem && tagName.equals("link", ignoreCase = true) -> link = parser.nextText()
                        insideItem && tagName.equals("pubDate", ignoreCase = true) -> pubDate = parser.nextText()
                        insideItem && tagName.equals("content:encoded", ignoreCase = true) -> {
                            val contentEncoded = parser.nextText()
                            val imgRegex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"]".toRegex()
                            val match = imgRegex.find(contentEncoded)
                            if (match != null) {
                                imageUrl = match.groupValues[1]
                                println("✅ Extracted from content: $imageUrl")
                            }
                        }


                    }
                }

                XmlPullParser.END_TAG -> {
                    if (tagName.equals("item", ignoreCase = true)) {
                        if (title != null && link != null && pubDate != null) {
                            println("✅ Adding article: $title - $imageUrl")
                            articles.add(
                                ArticleItem(
                                    title = title,
                                    link = link,
                                    pubDate = pubDate,
                                    imageUrl = imageUrl,
                                    source = "NASASpaceflight"
                                )
                            )
                        }
                        // Reset for next item
                        insideItem = false
                        title = null
                        link = null
                        pubDate = null
                        imageUrl = null
                    }
                }
            }
            eventType = parser.next()
        }

        return articles
    }
}
