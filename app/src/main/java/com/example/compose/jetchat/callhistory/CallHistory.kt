@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.compose.jetchat.callhistory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.jetchat.contact.SearchAction
import com.example.compose.jetchat.contact.SearchBox
import com.example.compose.jetchat.data.exampleCallHistoryUiState
import com.example.compose.jetchat.theme.JetchatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallHistoryContent(
    uiState: CallHistoryUiState,
    onSearchCallBack: (String, SearchAction) -> Unit = { _, _ -> },
    onRecall: (String) -> Unit = {},
    onMakeANewCall: () -> Unit = {},
) {
    Surface {
        Scaffold(
            floatingActionButton = { MakeCallButton(onMakeANewCall) }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                item {
                    Text(text = "Calls")
                }
                item {
                    SearchBox(onSearchCallBack)
                }

                item {
                    Text(text = "Recent Calls")
                }

                for (call in uiState.calls) {
                    item {
                        Call(
                            onItemClick = { },
                            call = call
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SearchBox(onSearchCallBack: (String, SearchAction) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.width(IntrinsicSize.Max),
        value = "Search",
        onValueChange = {
            onSearchCallBack(it, SearchAction.Typing)
        }
    )
}

enum class SearchAction {
    Typing,
}

@Composable
fun Call(
    onItemClick: (String) -> Unit,
    call: Call,
) {
    Row(modifier = Modifier.clickable {
        onItemClick(call.contactName)
    }) {
        Text(text = call.contactName, modifier = Modifier.weight(1f))
    }
}

@Composable
fun MakeCallButton(onMakeANewCall: () -> Unit) {
    FloatingActionButton(
        onClick = onMakeANewCall,
        containerColor = Color.DarkGray,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.VideoCall,
            contentDescription = "Make a call",
            tint = White,
        )
    }
}

@Preview
@Composable
fun CallHistoryPreview() {
    JetchatTheme {
        CallHistoryContent(
            uiState = exampleCallHistoryUiState,
        )
    }
}