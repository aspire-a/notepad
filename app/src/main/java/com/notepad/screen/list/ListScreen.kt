package com.notepad.screen.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notepad.model.UiEvent
import com.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    uiStateFlow: StateFlow<ListUiState>,
    uiEventFlow: Flow<UiEvent>,
    onNavigate: (route: String, data: Map<String, Any?>?) -> Unit,
    showToast: (String?) -> Unit,
    onEvent: (ListUiEvent) -> Unit
) {


    val uiState by uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {

        onEvent(ListUiEvent.GetNotes)

        uiEventFlow.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    onNavigate(event.route, event.data)
                }

                is UiEvent.ShowToast -> {
                    showToast(event.message)
                }
            }
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp), contentPadding = PaddingValues(0.dp)
    ) {
        items(uiState.noteList.size) {
            FilledTonalButton(
                onClick = {
                    onEvent(ListUiEvent.OnNoteClick(uiState.noteList[it].noteTitle))
                },
                shape = RectangleShape,
                modifier = Modifier,
            ) {
                Text(
                    text = uiState.noteList[it].noteTitle,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                )
            }
            Divider(color = Color.Black, thickness = 2.dp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListScreen() {
    NotepadTheme {
        ListScreen(
            uiStateFlow = MutableStateFlow(
                ListUiState()
            ),
            uiEventFlow = Channel<UiEvent>().receiveAsFlow(),
            onNavigate = { _, _ -> },
            showToast = { },
            onEvent = { }
        )
    }
}