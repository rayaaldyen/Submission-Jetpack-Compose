package com.example.jetrider.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetrider.data.RiderRepository
import com.example.jetrider.model.Rider
import com.example.jetrider.model.Riders
import com.example.jetrider.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RiderRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Riders>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Riders>>
        get() = _uiState

    fun getRiderById(riderId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getRiderById(riderId))
        }
    }

    fun addToFavorite(rider: Rider, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteRider(rider.id, isFavorite)
        }
    }
}