package com.notepad.screen.detail

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity

data class DetailUiState(
    val note: NotesEntity? = null,
)
