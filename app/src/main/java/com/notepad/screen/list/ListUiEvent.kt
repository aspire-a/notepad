package com.notepad.screen.list

sealed class ListUiEvent{
    data class OnNoteClick(val text: String): ListUiEvent()
    data class OnNoteLongPress(val textid: String): ListUiEvent()
    object OnAddClick : ListUiEvent()
    object GetNotes : ListUiEvent()
}
