package com.a_abdoun.spacexlaunchesapp.ui.launchdetail

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.a_abdoun.spacexlaunchesapp.data.model.*
import com.a_abdoun.spacexlaunchesapp.ui.components.YouTubePlayerView

@Composable
fun LaunchDetailScreen(viewModel: LaunchDetailViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (uiState) {
        is LaunchDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LaunchDetailUiState.Error -> {
            val message = (uiState as LaunchDetailUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Fehler: $message", color = Color.Red)
            }
        }

        is LaunchDetailUiState.Success -> {
            val launch = (uiState as LaunchDetailUiState.Success<Launch>).data
            LaunchDetailContent(launch = launch, onBack = onBack, context = context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailContent(launch: Launch, onBack: () -> Unit, context: android.content.Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = launch.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                launch.links.patch.large?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }



                    if (launch.links.youtube_id != null) {
                        val youtubeId = launch.links.youtube_id

                        Text(text = "Webcast", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        YouTubePlayerView(videoId = youtubeId)
                        Spacer(modifier = Modifier.height(16.dp))
                    }



                    Spacer(modifier = Modifier.height(16.dp))


                InfoText("Date UTC", launch.date_utc)
                InfoText("Date Local", launch.date_local)
                InfoText("Flight Number", launch.flight_number.toString())
                InfoText("Details", launch.details ?: "-")
                InfoText("Success", launch.success?.toString() ?: "Unknown")
                InfoText("Rocket ID", launch.rocket)
                InfoText("Launchpad ID", launch.launchpad)

                launch.static_fire_date_utc?.let {
                    InfoText("Static Fire Date", it)
                }
                launch.window?.let {
                    InfoText("Window", it.toString())
                }

                InfoText("Crew IDs", launch.crew.joinToString())
                InfoText("Ships", launch.ships.joinToString())
                InfoText("Capsules", launch.capsules.joinToString())
                InfoText("Payloads", launch.payloads.joinToString())

                launch.failures.forEachIndexed { i, failure ->
                    InfoText("Failure $i Reason", failure.reason)
                    InfoText("Failure $i Time", failure.time.toString())
                    InfoText("Failure $i Altitude", failure.altitude?.toString() ?: "Unknown")
                }

                launch.cores.forEachIndexed { i, core ->
                    InfoText("Core $i ID", core.core ?: "-")
                    InfoText("Flight", core.flight?.toString() ?: "-")
                    InfoText("Block", core.block?.toString() ?: "-")
                    InfoText("Reused", core.reused?.toString() ?: "-")
                    InfoText("Landing Type", core.landing_type ?: "-")
                    InfoText("Landpad", core.landpad ?: "-")
                }

                launch.fairings?.let {
                    InfoText("Fairings Reused", it.reused?.toString() ?: "-")
                    InfoText("Recovery Attempt", it.recovery_attempt?.toString() ?: "-")
                    InfoText("Recovered", it.recovered?.toString() ?: "-")
                    InfoText("Fairing Ships", it.ships.joinToString())
                }



                launch.links.article?.let { AddLinkButton("Article", it, context) }
                launch.links.wikipedia?.let { AddLinkButton("Wikipedia", it, context) }
            }
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = "$label:", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AddLinkButton(label: String, url: String, context: android.content.Context) {
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(label)
    }
}
