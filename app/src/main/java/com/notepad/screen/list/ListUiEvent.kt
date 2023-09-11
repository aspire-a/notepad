package com.notepad.screen.list

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity

sealed class ListUiEvent {
    data class OnNoteClick(val textid: Long) : ListUiEvent()
    data class OnNoteLongPress(val textid: String) : ListUiEvent()
    object OnAddClick : ListUiEvent()
    object GetNotes : ListUiEvent()
    data class AskPopUp(val notesEntity: NotesEntity?) : ListUiEvent()
}
