package com.notepad.data.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.NULL
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeMoshiAdapter @JvmOverloads constructor(private var formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME) :
    JsonAdapter<LocalDateTime?>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return when (reader.peek()) {
            NULL -> {
                reader.nextNull<Any>()
                null
            }

            else -> {
                val date = reader.nextString()
                LocalDateTime.parse(date)
            }
        }
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(formatter.format(value))
        }
    }

    fun setFormat(dateFormat: DateTimeFormatter) {
        formatter = dateFormat
    }

}