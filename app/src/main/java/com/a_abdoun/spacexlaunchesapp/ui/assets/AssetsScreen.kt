package com.a_abdoun.spacexlaunchesapp.ui.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.a_abdoun.spacexlaunchesapp.data.model.Rocket

@Composable
fun AssetsScreen(
    navController: NavHostController,
    viewModel: AssetsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is AssetsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AssetsUiState.Error -> {
            val msg = (state as AssetsUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Fehler: $msg")
            }
        }

        is AssetsUiState.Success -> {
            val rockets = (state as AssetsUiState.Success).rockets
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(rockets) { rocket ->
                    RocketCard(rocket) {
                        navController.navigate("rocketDetail/${rocket.id}")
                    }

                }
            }
        }
    }
}

@Composable
fun RocketCard(rocket: Rocket, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = rocket.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            if (rocket.flickr_images.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(rocket.flickr_images.first()),
                    contentDescription = "Rocket image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(text = "First Flight: ${rocket.first_flight}")
            Text(text = "Stages: ${rocket.stages}")
            Text(text = "Success Rate: ${rocket.success_rate_pct}%")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = rocket.description, textAlign = TextAlign.Justify)
        }
    }
}
