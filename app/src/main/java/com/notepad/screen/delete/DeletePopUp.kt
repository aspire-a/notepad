package com.notepad.screen.delete

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.notepad.R
import com.notepad.data.datasource.local.roomdb.entity.NotesEntity
import com.notepad.model.UiEvent
import com.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DeletePopUp(
    uiEventFlow: Flow<UiEvent>,
    onNavigate: (route: String, data: Map<String, Any?>?) -> Unit,
    showToast: (String?) -> Unit,
    onEvent: (DeleteUiEvent) -> Unit,
    closeDialog: () -> Unit,
    noteEntity: NotesEntity?,
) {


    LaunchedEffect(Unit) {
        uiEventFlow.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    onNavigate(event.route, event.data)
                }

                is UiEvent.ShowToast -> {
                    showToast(event.message)
                }


                UiEvent.ClosePopUp -> {
                    closeDialog()
                }

                is UiEvent.ShowPopUp<*> -> {

                }
            }
        }
    }


    Dialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.Inherit,
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = true
        )
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.LightGray, shape = RectangleShape)
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.delete_popup_description))
            Row(


            ) {
                FilledTonalButton(
                    onClick = {
                        onEvent(DeleteUiEvent.Approve(noteEntity))
                    }, modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.approve_text))
                }
                FilledTonalButton(
                    onClick = {
                        onEvent(DeleteUiEvent.Cancel)
                    }, modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.decline_text))
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewDeletePopUp() {
    NotepadTheme {
        DeletePopUp(
            uiEventFlow = Channel<UiEvent>().receiveAsFlow(),
            onNavigate = { _, _ -> },
            showToast = { },
            onEvent = { },
            closeDialog = { },
            noteEntity = null,

            )
    }
}