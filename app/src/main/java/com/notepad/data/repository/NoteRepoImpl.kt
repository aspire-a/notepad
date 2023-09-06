package com.notepad.data.repository

import com.notepad.data.datasource.local.roomdb.NotesDAO
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override suspend fun updateNote(notesEntity: NotesEntity): Flow<Unit> {
        return flow {
            notesDAO.updateNote(notesEntity)
            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addNote(text: String): Flow<Unit> {
        return flow {
          notesDAO.addNote(
            notesEntity = NotesEntity(
              noteCreationDate = LocalDateTime.now(),
              noteUpdateDate = LocalDateTime.now(),
              noteTitle = text,
              noteValue = text,
            )
          )
          emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteNote(notesEntity: NotesEntity): Flow<Unit> {
        return flow {
          notesDAO.deleteNote(notesEntity)
          emit(Unit)
        }.flowOn(Dispatchers.IO)
    }
}