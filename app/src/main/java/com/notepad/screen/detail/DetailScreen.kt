package com.notepad.screen.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.notepad.model.UiEvent
import com.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    noteId: Long?,
    uiStateFlow: StateFlow<DetailUiState>,
    uiEventFlow: Flow<UiEvent>,
    onNavigate: (route: String, data: Map<String, Any?>?) -> Unit,
    showToast: (String?) -> Unit,
    onEvent: (DetailUiEvent) -> Unit
) {

    val uiState by uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {

        onEvent(DetailUiEvent.GetNoteById(noteId = noteId))

        uiEventFlow.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    onNavigate(event.route, event.data)
                }

                is UiEvent.ShowToast -> {
                    showToast(event.message)
                }

                else -> {

                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(DetailUiEvent.OnNoteSave)
        }
    }


    TextField(
        modifier = Modifier.fillMaxSize(), value = uiState.note?.noteValue ?: "", onValueChange = {
            onEvent(DetailUiEvent.OnValueChange(it))
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    NotepadTheme {
        DetailScreen(noteId = 1,
            uiStateFlow = MutableStateFlow(
                DetailUiState()
            ),
            uiEventFlow = Channel<UiEvent>().receiveAsFlow(),
            onNavigate = { _, _ -> },
            showToast = { },
            onEvent = {})
    }
}