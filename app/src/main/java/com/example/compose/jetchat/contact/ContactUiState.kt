package com.example.compose.jetchat.contact

import androidx.compose.runtime.Immutable

class ContactUiState(
    val contacts: List<Contact>,
)

@Immutable
data class Contact(
    val name: String,
)