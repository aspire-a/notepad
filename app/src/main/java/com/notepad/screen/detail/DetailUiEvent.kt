package com.notepad.screen.detail

sealed class DetailUiEvent {
    object OnNoteSave : DetailUiEvent()
    data class OnValueChange(val text: String) : DetailUiEvent()
    data class GetNoteById(val noteId: Long?) : DetailUiEvent()
}
