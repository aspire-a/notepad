package com.notepad.data.repository

import com.notepad.data.datasource.local.roomdb.NotesDAO
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

class NoteRepoImpl(
    private val notesDAO: NotesDAO
) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<NotesEntity>> {
        return notesDAO.getAllNotes()
    }

    override suspend fun getNoteById(noteId: Long): Flow<NotesEntity> {
        return notesDAO.getNoteById(noteId)
    }

    override suspend fun updateNote(notesEntity: NotesEntity) {
        return notesDAO.updateNote(notesEntity)
    }

    override suspend fun addNote(notesEntity: NotesEntity) {
        return notesDAO.addNote(notesEntity)
    }

    override suspend fun deleteNote(notesEntity: NotesEntity) {
        return notesDAO.deleteNote(notesEntity)
    }
}