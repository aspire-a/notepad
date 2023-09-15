package com.notepad.util

import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import java.text.Collator
import java.util.Locale

fun <T> List<T>.sortNoteListByUpdateDate(): List<T> {
    return this.sortedWith { s1, s2 ->
        Collator.getInstance(Locale.ENGLISH).compare(
            (s2 as NotesEntity).noteUpdateDate.getNoteDateFormat(),
            (s1 as NotesEntity).noteUpdateDate.getNoteDateFormat()
        )
    }
}

fun <T> List<T>.sortNoteListByName(): List<T> {
    return this.sortedWith { s1, s2 ->
        Collator.getInstance(Locale.ENGLISH).compare(
            (s1 as NotesEntity).noteValue,
            (s2 as NotesEntity).noteValue
        )
    }
}