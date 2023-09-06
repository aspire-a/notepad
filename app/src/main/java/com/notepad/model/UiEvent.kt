package com.notepad.model

sealed class UiEvent {
    data class Navigate<T>(val route: String, val data: Map<String, T?>? = null) : UiEvent()
    data class ShowToast(val message: String?) : UiEvent()
}