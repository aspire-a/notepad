package com.notepad.model
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHandler {
    val handler = CoroutineExceptionHandler { _, throwable ->
    }
}