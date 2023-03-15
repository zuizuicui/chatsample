@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.compose.jetchat.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetchat.data.exampleContactUiState
import com.example.compose.jetchat.theme.JetchatTheme

@Composable
fun ContactContent(
    uiState: ContactUiState,
    onSearchCallBack: (String, SearchAction) -> Unit = { _, _ -> },
    onAudioCallClick: (String) -> Unit = {},
    onVideoCallClick: (String) -> Unit = {},
) {
    Surface {
        LazyColumn {
            item {
                Text(text = "Contacts")
            }
            item {
                SearchBox(onSearchCallBack)
            }

            val contacts = uiState.contacts
            fun Contact.firstCharName() = name.first()
            for (index in contacts.indices) {
                val prevFirstCharName = contacts.getOrNull(index - 1)?.firstCharName()
                val contact = contacts[index]
                val isFirstContactWithNewCharName = prevFirstCharName != contact.firstCharName()

                if (isFirstContactWithNewCharName) {
                    item {
                        Text(text = contact.firstCharName().toString())
                    }
                }

                item {
                    Contact(
                        onAudioCallClick = onAudioCallClick,
                        onVideoCallClick = onVideoCallClick,
                        contact = contact
                    )
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
fun Contact(
    onAudioCallClick: (String) -> Unit,
    onVideoCallClick: (String) -> Unit,
    contact: Contact,
) {
    Row {
        Text(text = contact.name, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier.clickable { onAudioCallClick(contact.name) },
            imageVector = Icons.Filled.Call, contentDescription = "Audio call"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier.clickable { onVideoCallClick(contact.name) },
            imageVector = Icons.Filled.VideoCall, contentDescription = "Audio call"
        )
    }
}

@Preview
@Composable
fun ContactPreview() {
    JetchatTheme {
        ContactContent(
            uiState = exampleContactUiState,
        )
    }
}