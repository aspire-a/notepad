package com.notepad.screen.list

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity

data class ListUiState(
    val noteList: List<NotesEntity> = listOf()
)
