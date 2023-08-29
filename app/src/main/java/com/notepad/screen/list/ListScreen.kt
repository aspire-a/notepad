package com.notepad.screen.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notepad.model.UiEvent
import com.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
fun ListScreen(
    uiStateFlow: StateFlow<ListUiState>,
    uiEventFlow: Flow<UiEvent>,
    onNavigate: (route:String, data: Map<String, Any?>?) -> Unit,
    onEvent: (ListUiEvent) -> Unit
) {


    val uiState by uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {

        uiEventFlow.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    onNavigate(event.route, event.data)
                }

                else -> {}
            }
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(uiState.noteList.size) {
            Button(
                onClick = {
                          onEvent(ListUiEvent.OnNoteClick(uiState.noteList[it]))
                }, modifier = Modifier
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = uiState.noteList[it],
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
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
            onNavigate = {_, _ ->},
            onEvent = {}
        )
    }
}