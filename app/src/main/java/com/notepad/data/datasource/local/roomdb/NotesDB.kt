package com.notepad.data.datasource.local.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity


@Database(
    entities = [
        NotesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NotesDB : RoomDatabase() {
    abstract fun noteDAO(): NotesDAO

    companion object {
        @Volatile
        private var INSTANCE: NotesDB? = null

        fun getInstance(context: Context): NotesDB {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "notes.db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}