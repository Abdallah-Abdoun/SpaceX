package com.a_abdoun.spacexlaunchesapp.ui.news.reddit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.a_abdoun.spacexlaunchesapp.data.model.Launch

@Composable
fun LaunchRedditCard(
    launch: Launch,
    onClick: (String) -> Unit
) {
    val imageUrl = when {
        launch.links.flickr.original.isNotEmpty() -> launch.links.flickr.original.first()
        !launch.links.patch.large.isNullOrBlank() -> launch.links.patch.large
        else -> null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            imageUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(text = launch.name, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = launch.date_utc)
            }

            Spacer(modifier = Modifier.height(4.dp))

            launch.links.reddit.launch?.let { link ->
                Row(
                    modifier = Modifier
                        .clickable { onClick(link) }
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Launch")
                }
            }
        }
    }
}
