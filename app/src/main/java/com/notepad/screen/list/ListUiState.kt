package com.notepad.screen.list

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity

data class ListUiState(
    var noteList: List<NotesEntity> = listOf()
)
