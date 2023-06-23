package com.example.jetrider.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetrider.data.RiderRepository
import com.example.jetrider.model.Riders
import com.example.jetrider.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RiderRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Riders>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Riders>>>
        get() = _uiState

    fun getRiders() {
        viewModelScope.launch {
            repository.getRiders()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { riders ->
                    _uiState.value = UiState.Success(riders)
                }
        }
    }

    private val _query = mutableStateOf(QueryState())
    val query: State<QueryState> = _query

    private fun search(newQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchRiders(newQuery)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = _query.value.copy(query = newQuery)
        search(newQuery)
    }
}
