package com.notepad.screen.add

sealed class AddUiEvent {
    object OnNoteSave : AddUiEvent()
    data class OnValueChange(val text: String) : AddUiEvent()
    object FocusRequest : AddUiEvent()
}
