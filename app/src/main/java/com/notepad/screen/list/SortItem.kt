package com.notepad.screen.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.notepad.R
import com.notepad.model.SortModel

@ExperimentalMaterial3Api
@Composable
fun SortItem(
    modifier: Modifier = Modifier,
    onClick: (SortModel) -> Unit
) {
    var localeDropDownState by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
            .clickable(
                onClick = {
                    localeDropDownState = true
                }
            )
    ) {
        Icon(painter = painterResource(id = R.drawable.sort_list_icon), contentDescription = "")
        DropdownMenu(expanded = localeDropDownState,
            onDismissRequest = {
                localeDropDownState = false
            }
        ) {
            SortModel.values().forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.displayName,
                            textAlign = TextAlign.Center,
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        localeDropDownState = false
                        onClick(it)
                    },
                    /*leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(end = 4.dp),
                            tint = Color.Unspecified,
                            painter = painterResource(id = it.icon),
                            contentDescription = stringResource(id = R.string.common_icon_content_description)
                        )
                    }*/
                )
            }
        }
    }
}