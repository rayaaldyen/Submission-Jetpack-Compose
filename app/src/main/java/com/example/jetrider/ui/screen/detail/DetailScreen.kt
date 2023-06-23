package com.example.jetrider.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jetrider.R
import com.example.jetrider.di.Injection
import com.example.jetrider.ui.ViewModelFactory
import com.example.jetrider.ui.common.UiState
import com.example.jetrider.ui.components.ErrorMessage

@Composable
fun DetailScreen(
    riderId: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRiderById(riderId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.rider.name,
                    data.rider.nation,
                    data.rider.number,
                    data.rider.team,
                    data.rider.photoUrl,
                    data.rider.bio,
                    data.isFavorite,
                    onBackClick = navigateBack,
                    onAddToFavorite = { isFavorite ->
                        viewModel.addToFavorite(data.rider, isFavorite)
                    }
                )
            }
            is UiState.Error -> {
                ErrorMessage()
            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    nation: String,
    number: String,
    team: String,
    photoUrl: String,
    bio: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddToFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFavoriteRider by rememberSaveable { mutableStateOf(isFavorite) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = stringResource(R.string.nation, nation),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.number, number),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.team, team),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = bio,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    isFavoriteRider = !isFavoriteRider
                    onAddToFavorite(isFavoriteRider)
                },
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = if (isFavoriteRider) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (isFavoriteRider) Color.Red else Color.White,
                    contentDescription = "Add to favorite"
                )
            }
        }
    }
}