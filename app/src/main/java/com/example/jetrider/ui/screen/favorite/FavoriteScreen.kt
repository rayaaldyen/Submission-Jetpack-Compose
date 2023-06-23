package com.example.jetrider.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetrider.di.Injection
import com.example.jetrider.model.Riders
import com.example.jetrider.ui.ViewModelFactory
import com.example.jetrider.ui.common.UiState
import com.example.jetrider.ui.components.EmptyContent
import com.example.jetrider.ui.components.ErrorMessage
import com.example.jetrider.ui.components.RiderListItem


@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteRiders()
            }
            is UiState.Success -> {
                FavoriteContent(
                    riders = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                ErrorMessage()
            }
        }
    }

}

@Composable
fun FavoriteContent(
    riders: List<Riders>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    Box(modifier = modifier) {
        if (riders.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {

                items(riders) { data ->
                    RiderListItem(
                        name = data.rider.name,
                        nation = data.rider.nation,
                        number = data.rider.number,
                        team = data.rider.team,
                        photoUrl = data.rider.photoUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.rider.id)
                        }
                    )
                }
            }
        }
    }
}
