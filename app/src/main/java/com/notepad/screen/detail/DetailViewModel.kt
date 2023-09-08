package com.notepad.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
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
class DetailViewModel @Inject constructor(
    private val noteRepo: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(addUiEvent: DetailUiEvent) {
        when (addUiEvent) {
            is DetailUiEvent.OnNoteSave -> {
                onNoteUpdate()
            }

            is DetailUiEvent.OnValueChange -> {
                onValueChange(addUiEvent.text)
            }

            is DetailUiEvent.GetNoteById -> {
                getNoteById(addUiEvent.noteId)
            }
        }
    }

    private fun onNoteUpdate() {
        viewModelScope.launch(ExceptionHandler.handler) {

            val note = _uiState.value.note



            note?.let {
                if (note.noteValue.isNotBlank()) {
                    noteRepo.updateNote(note).catch {
                        _uiEvent.send(UiEvent.ShowToast(it.message))
                    }.collect {

                    }
                } else {
                    noteRepo.deleteNote(note)
                        .catch {
                            _uiEvent.send(UiEvent.ShowToast(it.message))
                        }.collect {
                        }
                }
            }
        }
    }

    private fun onValueChange(note: String) {
        viewModelScope.launch(ExceptionHandler.handler) {

            var newNote: NotesEntity? = null

            _uiState.value.note?.let {
                newNote = NotesEntity(
                    noteId = it.noteId,
                    noteCreationDate = it.noteCreationDate,
                    noteUpdateDate = it.noteUpdateDate,
                    noteValue = note
                )
            }

            _uiState.update {
                it.copy(
                    note = newNote
                )
            }
        }
    }


    private fun onValueChange(note: NotesEntity) {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiState.update {
                it.copy(
                    note = note
                )
            }
        }
    }


    private fun getNoteById(noteId: Long?) {
        viewModelScope.launch(ExceptionHandler.handler) {

            noteId?.let {
                noteRepo.getNoteById(noteId).catch {
                    _uiEvent.send(UiEvent.ShowToast(it.message))
                }.collect {
                    onValueChange(it)

                }
            }
        }
    }
}