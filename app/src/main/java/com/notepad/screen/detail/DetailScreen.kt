package com.notepad.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notepad.ui.theme.NotepadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    textField: String,
    onValueChange: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            TextField(
                value = textField,
                onValueChange = {
                    onValueChange(it)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    NotepadTheme {
        DetailScreen(
            textField = "denememememem uzun bir metinemem uzun bir metiemem uzun bir metiemem uzun bir metiemem uzun bir metiemem uzun bir meti",
            onValueChange = {}
        )
    }
}