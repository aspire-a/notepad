package com.notepad.data.adapter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class LocalDateTypeConverter {
    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return try {
            LocalDate.parse(
                dateString,
                DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.ENGLISH)
            )
        } catch (e: Exception) {
            throw e
        }
    }

    @TypeConverter
    fun toDateString(localDate: LocalDate): String {
        return try {
            DateTimeFormatter.ofPattern("dd.MM.yyyy")
                .withLocale(Locale.ENGLISH)
                .format(localDate)
        } catch (e: Exception) {
            throw e
        }
    }
}