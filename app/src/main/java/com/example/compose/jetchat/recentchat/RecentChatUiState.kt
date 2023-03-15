package com.example.compose.jetchat.recentchat

import androidx.compose.runtime.Immutable

class RecentChatUiState(
    val chats: List<Chat>,
)

@Immutable
data class Chat(
    val channel: String,
)