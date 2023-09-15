package com.notepad.model


sealed class UiEvent {
    data class Navigate<T>(val route: String, val data: Map<String, T?>? = null) : UiEvent()
    data class ShowToast(val message: String?) : UiEvent()
    data class ShowPopUp<T>(val route: String, val data: Map<String, T?>? = null) : UiEvent()
    object ClosePopUp : UiEvent()
    data class FocusRequester(val focusRequester: androidx.compose.ui.focus.FocusRequester) :
        UiEvent()
}