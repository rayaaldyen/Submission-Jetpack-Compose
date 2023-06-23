package com.example.jetrider.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun RiderListItem(
    name: String,
    nation: String,
    number: String,
    team: String,
    photoUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 16.sp),
            )
            Text(
                text = nation,
                fontWeight = FontWeight.Medium,
                style = TextStyle(fontSize = 12.sp)
            )
            Text(
                text = number,
                fontWeight = FontWeight.Medium,
                style = TextStyle(fontSize = 12.sp)
            )
            Text(
                text = team,
                fontWeight = FontWeight.Medium,
                style = TextStyle(fontSize = 12.sp)
            )
        }

    }
}
