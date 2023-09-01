package com.notepad.data.datasource.local.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDAO {
    @Query(
        """
            SELECT * FROM notesentity
        """
    )
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query(
        """
            SELECT * FROM notesentity 
            WHERE noteId = :noteId
        """
    )
    fun getNoteById(noteId: Long): Flow<NotesEntity>

    @Update
    fun updateNote(notesEntity: NotesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(notesEntity: NotesEntity)

    @Delete
    fun deleteNote(notesEntity: NotesEntity)
}