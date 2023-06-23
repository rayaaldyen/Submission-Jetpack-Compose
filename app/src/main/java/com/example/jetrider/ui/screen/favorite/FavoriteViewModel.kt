package com.example.jetrider.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetrider.data.RiderRepository
import com.example.jetrider.model.Riders
import com.example.jetrider.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: RiderRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Riders>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Riders>>>
        get() = _uiState

    fun getFavoriteRiders() {
        viewModelScope.launch {
            repository.getFavoriteRider()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { riders ->
                    _uiState.value = UiState.Success(riders)
                }
        }
    }

}
