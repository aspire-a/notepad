package com.notepad.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notepad.data.repository.NoteRepository
import com.notepad.model.ExceptionHandler
import com.notepad.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(
    private val noteRepo: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(addUiEvent: AddUiEvent) {
        when (addUiEvent) {
            is AddUiEvent.OnNoteSave -> {
                onNoteSave()
            }

            is AddUiEvent.OnValueChange -> {
                onValueChange(addUiEvent.text)
            }
        }
    }

    private fun onNoteSave() {
        viewModelScope.launch(ExceptionHandler.handler) {
            noteRepo.addNote(_uiState.value.note)
                .catch {
                    _uiEvent.send(UiEvent.ShowToast(it.message))
                }
                .collect {
                    onValueChange("")
                }
        }
    }

    private fun onValueChange(note: String) {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiState.update {
                it.copy(
                    note = note
                )
            }
        }
    }
}