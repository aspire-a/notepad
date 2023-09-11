package com.notepad.screen.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.model.UiEvent
import com.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    uiStateFlow: StateFlow<ListUiState>,
    uiEventFlow: Flow<UiEvent>,
    onNavigate: (route: String, data: Map<String, Any?>?) -> Unit,
    showToast: (String?) -> Unit,
    onEvent: (ListUiEvent) -> Unit,
    showDialog: (String, data: Map<String, Any?>?) -> Unit,
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

                is UiEvent.ShowPopUp<*> -> {
                    showDialog(event.route, event.data)
                }

                UiEvent.ClosePopUp -> {

                }
            }
        }
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(uiState.noteList.size) {
            Box(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .fillMaxWidth()
                    .combinedClickable(

                        onClick = {
                            onEvent(ListUiEvent.OnNoteClick(uiState.noteList[it].noteId))
                        }, onLongClick = {

                            onEvent(ListUiEvent.AskPopUp(uiState.noteList[it]))
                        }),
            ) {
                Text(
                    text = uiState.noteList[it].noteValue,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
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
                ListUiState(
                    noteList = listOf(
                        NotesEntity(
                            1, LocalDateTime.now(), LocalDateTime.now(), "deneme"
                        ),
                        NotesEntity(
                            1, LocalDateTime.now(), LocalDateTime.now(), "patates"
                        ),
                        NotesEntity(
                            1, LocalDateTime.now(), LocalDateTime.now(), "123"
                        ),
                    )
                )
            ),
            uiEventFlow = Channel<UiEvent>().receiveAsFlow(),
            onNavigate = { _, _ -> },
            showToast = { },
            onEvent = { },
            showDialog = { _, _ -> },
        )
    }
}