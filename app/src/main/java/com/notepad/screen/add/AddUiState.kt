package com.notepad.screen.add

import androidx.compose.ui.focus.FocusRequester

data class AddUiState(
    val note: String = "",
    val focusRequest: FocusRequester = FocusRequester(),
)
