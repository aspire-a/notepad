package com.notepad.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.data.repository.NoteRepository
import com.notepad.model.ExceptionHandler
import com.notepad.model.UiEvent
import com.notepad.navigation.Route
import com.notepad.util.sortNoteListByUpdateDate
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
class ListViewModel @Inject constructor(
    private val noteRepo: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(listUiEvent: ListUiEvent) {
        when (listUiEvent) {
            ListUiEvent.OnAddClick -> {

            }

            is ListUiEvent.OnNoteClick -> {
                onNavigateDetail(listUiEvent.textid)
            }

            is ListUiEvent.OnNoteLongPress -> {

            }

            ListUiEvent.GetNotes -> {
                getNotes()
            }

            is ListUiEvent.AskPopUp -> {
                askPopUp(listUiEvent.notesEntity)
            }
        }
    }

    private fun onNavigateDetail(textId: Long) {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = Route.DETAIL.name,
                    data = mapOf<String, Any>(
                        "DetailData" to textId
                    )
                )
            )
        }
    }

    private fun getNotes() {
        viewModelScope.launch(ExceptionHandler.handler) {
            noteRepo.getAllNotes()
                .catch {
                    _uiEvent.send(UiEvent.ShowToast(it.message))
                }
                .collect { noteList ->
                    _uiState.update { state ->
                        state.copy(
                            noteList = noteList.sortNoteListByUpdateDate()
                        )
                    }
                }
        }
    }


    private fun askPopUp(notesEntity: NotesEntity?) {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiEvent.send(
                UiEvent.ShowPopUp(
                    route = Route.DELETE.name,
                    data = mapOf(
                        "DeleteData" to notesEntity
                    )
                )
            )
        }
    }


}