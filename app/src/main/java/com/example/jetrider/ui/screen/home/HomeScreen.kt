package com.example.jetrider.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.jetrider.ui.components.SearchBar


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val queryState by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRiders()
            }
            is UiState.Success -> {
                HomeContent(
                    riders = uiState.data,
                    modifier = modifier,
                    query = queryState.query,
                    onQueryChange = viewModel::onQueryChange,
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
fun HomeContent(
    riders: List<Riders>,
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Box(modifier = modifier) {

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
            if (riders.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    EmptyContent()
                }
            } else {
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


