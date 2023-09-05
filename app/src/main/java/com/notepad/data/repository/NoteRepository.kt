package com.notepad.data.repository

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getAllNotes(): Flow<List<NotesEntity>>
    suspend fun getNoteById(noteId: Long): Flow<NotesEntity>
    suspend fun updateNote(notesEntity: NotesEntity)
    suspend fun addNote(text: String)
    suspend fun deleteNote(notesEntity: NotesEntity)
}