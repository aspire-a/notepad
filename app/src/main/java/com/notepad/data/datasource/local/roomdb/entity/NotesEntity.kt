package com.notepad.data.datasource.local.roomdb.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class NotesEntity(
    @PrimaryKey
    val noteId: Long,
    val noteCreationDate: String,
    val noteUpdateDate: String,
    val noteTitle: String,
    val noteValue: String,

    ) : Parcelable