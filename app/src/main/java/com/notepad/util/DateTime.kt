package com.notepad.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime?.getNoteDateFormat(): String {
    this?.let {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withLocale(Locale.ENGLISH)
            .format(this)
    }
    return ""
}