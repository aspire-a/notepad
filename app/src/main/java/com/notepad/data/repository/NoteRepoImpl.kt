package com.notepad.data.repository

import com.notepad.data.datasource.local.roomdb.NotesDAO
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

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

    override suspend fun addNote(text: String) {
        return notesDAO.addNote(
            notesEntity = NotesEntity(
                noteCreationDate = LocalDateTime.now(),
                noteTitle = text,
                noteValue = text,
            )
        )
    }

    override suspend fun deleteNote(notesEntity: NotesEntity) {
        return notesDAO.deleteNote(notesEntity)
    }
}