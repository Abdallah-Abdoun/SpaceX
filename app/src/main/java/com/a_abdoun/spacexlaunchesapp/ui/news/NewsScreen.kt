package com.a_abdoun.spacexlaunchesapp.ui.news

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.a_abdoun.spacexlaunchesapp.data.model.Launch
import com.a_abdoun.spacexlaunchesapp.ui.news.articles.ArticleCard
import com.a_abdoun.spacexlaunchesapp.ui.news.articles.ArticlesUiState
import com.a_abdoun.spacexlaunchesapp.ui.news.articles.ArticlesViewModel
import com.a_abdoun.spacexlaunchesapp.ui.news.reddit.LaunchRedditCard

@Composable
fun NewsScreen(
    navController: NavHostController,
    viewModel: NewsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Articles", "Reddit")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (state) {
            is NewsUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NewsUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Fehler beim Laden")
                }
            }

            is NewsUiState.Success -> {
                val allLaunches = (state as NewsUiState.Success).launches

                when (selectedTabIndex) {
                    0 -> ArticlesTab(navController)
                    1 -> RedditTab(navController, allLaunches)
                }
            }
        }
    }
}



@Composable
fun ArticlesTab(
    navController: NavHostController,
    viewModel: ArticlesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is ArticlesUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ArticlesUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Fehler beim Laden")
            }
        }

        is ArticlesUiState.Success -> {
            val articles = (state as ArticlesUiState.Success).articles

            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(articles) { article ->
                    ArticleCard(article) {
                        val encoded = Uri.encode(it)
                        navController.navigate("webview/$encoded")
                    }

                }
            }
        }
    }
}

@Composable
fun RedditTab(
    navController: NavHostController,
    launches: List<Launch>
) {
    val filtered = launches.filter {
        it.links.reddit.campaign != null ||
                it.links.reddit.launch != null ||
                it.links.reddit.media != null ||
                it.links.reddit.recovery != null
    }

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(filtered) { launch ->
            LaunchRedditCard(launch) {
                val encoded = Uri.encode(it)
                navController.navigate("webview/$encoded")
            }        }
    }
}
