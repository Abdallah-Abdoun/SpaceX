package com.a_abdoun.spacexlaunchesapp.ui.launchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.a_abdoun.spacexlaunchesapp.data.model.Launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LaunchListScreen(
    viewModel: LaunchListViewModel,
    onLaunchClick: (Launch) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Upcoming", "Past")

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

        when (uiState) {
            is LaunchListUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LaunchListUiState.Error -> {
                val msg = (uiState as LaunchListUiState.Error).message
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Fehler: $msg", color = MaterialTheme.colorScheme.error)
                }
            }

            is LaunchListUiState.Success -> {
                val launches = (uiState as LaunchListUiState.Success).launches
                //val now = System.currentTimeMillis() / 1000
                val filteredLaunches = when (selectedTabIndex) {
                    0 -> launches.filter { it.upcoming } // Upcoming
                    else -> launches.filter { !it.upcoming } // Past
                }

                LaunchListContent(filteredLaunches, onLaunchClick)
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun LaunchListContent(
    launches: List<Launch>,
    onLaunchClick: (Launch) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(launches) { launch ->
            val parsedDate = try {
                ZonedDateTime.parse(launch.date_utc)
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            } catch (e: Exception) {
                launch.date_utc
            }

            ListItem(
                modifier = Modifier
                    .clickable { onLaunchClick(launch) }
                    .padding(horizontal = 8.dp),
                headlineContent = { Text(launch.name) },
                supportingContent = {
                    Column {
                        Text(text = parsedDate)
                        when (launch.success) {
                            true -> Text("Erfolgreich", color = Color.Green)
                            false -> Text("Fehlgeschlagen", color = Color.Red)
                            null -> Text("Noch nicht gestartet", color = Color.Gray)
                        }

                    }
                }
            )
            HorizontalDivider()
        }
    }
}
