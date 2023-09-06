package com.notepad.data.datasource.local.roomdb.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity
@Parcelize
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    val noteCreationDate: LocalDateTime,
    val noteUpdateDate: LocalDateTime,
    val noteTitle: String,
    val noteValue: String
) : Parcelable