package com.notepad.screen.list

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.model.SortModel

data class ListUiState(
    val noteList: List<NotesEntity> = listOf(),
    val listSortModel: SortModel = SortModel.DATE,
)
