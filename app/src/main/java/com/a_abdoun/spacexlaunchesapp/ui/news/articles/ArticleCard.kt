package com.a_abdoun.spacexlaunchesapp.ui.news.articles

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.core.net.toUri

@Composable
fun ArticleCard(
    item: ArticleItem,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(item.link) }

    ) {
        Column {
            item.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }


            Spacer(modifier = Modifier.height(8.dp))
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(item.source, style = MaterialTheme.typography.labelSmall)
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.pubDate, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
