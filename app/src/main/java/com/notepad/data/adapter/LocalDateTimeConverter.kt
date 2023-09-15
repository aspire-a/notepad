package com.notepad.data.adapter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime {
        return try {
            LocalDateTime.parse(
                dateString,
                DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss").withLocale(Locale.ENGLISH)
            )
        } catch (e: Exception) {//Exception Handler
            throw e
        }
    }

    @TypeConverter
    fun toDateTimeString(localDateTime: LocalDateTime): String {
        return try {
            DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss")
                .withLocale(Locale.ENGLISH)
                .format(localDateTime)
        } catch (e: Exception) {
            throw e
        }
    }
}