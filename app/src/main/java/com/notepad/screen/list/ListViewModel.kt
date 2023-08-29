package com.notepad.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notepad.model.ExceptionHandler
import com.notepad.model.UiEvent
import com.notepad.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(listUiEvent: ListUiEvent) {
        when (listUiEvent) {
            ListUiEvent.OnAddClick -> {

            }

            is ListUiEvent.OnNoteClick -> {
                onNavigateDetail(listUiEvent.text)
            }

            is ListUiEvent.OnNoteLongPress -> {

            }
        }
    }

    private fun onNavigateDetail(text: String) {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = Route.DETAIL.name,
                    data = mapOf<String, Any>(
                        "DetailData" to text
                    )
                )
            )
        }
    }
}