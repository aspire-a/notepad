package com.notepad.screen.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.data.repository.NoteRepository
import com.notepad.model.ExceptionHandler
import com.notepad.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeleteViewModel @Inject constructor(
    private val noteRepo: NoteRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(deleteUiEvent: DeleteUiEvent) {
        when (deleteUiEvent) {
            is DeleteUiEvent.Approve -> {
                approve(noteEntity = deleteUiEvent.noteEntity)
            }

            DeleteUiEvent.Cancel -> {
                cancel()
            }
        }
    }

    private fun approve(noteEntity: NotesEntity?) {
        viewModelScope.launch(ExceptionHandler.handler) {
            noteEntity?.let {
                noteRepo.deleteNote(noteEntity)
                    .catch {
                        _uiEvent.send(UiEvent.ShowToast(it.message))
                    }.collect {
                        _uiEvent.send(
                            UiEvent.ClosePopUp
                        )
                    }
            }
        }
    }

    private fun cancel() {
        viewModelScope.launch(ExceptionHandler.handler) {
            _uiEvent.send(
                UiEvent.ClosePopUp
            )
        }
    }
}