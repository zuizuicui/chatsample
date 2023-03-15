@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.compose.jetchat.recentchat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.jetchat.data.exampleRecentChatUiState
import com.example.compose.jetchat.theme.JetchatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentChatContent(
    uiState: RecentChatUiState,
    onOpenConversation: (String) -> Unit = {},
    onNewChat: () -> Unit = {},
) {
    Surface {
        Scaffold(
            floatingActionButton = { NewChatButton(onNewChat) }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                item {
                    Text(text = "Messages")
                }

                for (chat in uiState.chats) {
                    item {
                        Chat(
                            onItemClick = onOpenConversation,
                            chat = chat
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Chat(
    onItemClick: (String) -> Unit,
    chat: Chat,
) {
    Row(modifier = Modifier.clickable {
        onItemClick(chat.channel)
    }) {
        Text(text = chat.channel, modifier = Modifier.weight(1f))
    }
}

@Composable
fun NewChatButton(onMakeANewCall: () -> Unit) {
    FloatingActionButton(
        onClick = onMakeANewCall,
        containerColor = Color.DarkGray,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Make a call",
            tint = White,
        )
    }
}

@Preview
@Composable
fun RecentChatPreview() {
    JetchatTheme {
        RecentChatContent(
            uiState = exampleRecentChatUiState,
        )
    }
}