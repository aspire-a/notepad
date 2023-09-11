package com.notepad.screen.delete

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity

sealed class DeleteUiEvent {
    data class Approve(val noteEntity: NotesEntity?) : DeleteUiEvent()
    object Cancel : DeleteUiEvent()
}
