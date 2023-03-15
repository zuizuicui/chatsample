package com.example.compose.jetchat.callhistory

import androidx.compose.runtime.Immutable

class CallHistoryUiState(
    val calls: List<Call>,
)

@Immutable
data class Call(
    val contactName: String,
)