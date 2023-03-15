package com.example.compose.jetchat.recentchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.compose.jetchat.NavGraphDestinations
import com.example.compose.jetchat.R
import com.example.compose.jetchat.contact.ContactContent
import com.example.compose.jetchat.data.exampleContactUiState
import com.example.compose.jetchat.data.exampleRecentChatUiState
import com.example.compose.jetchat.theme.JetchatTheme

class RecentChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setContent {
            JetchatTheme {
                RecentChatContent(
                    exampleRecentChatUiState,
                    onOpenConversation = {
                        findNavController().navigate(
                            R.id.nav_home,
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentCallScreen(navController: NavController) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    RecentChatContent(
        exampleRecentChatUiState,
        onOpenConversation = {
            navController.navigate(NavGraphDestinations.Home)
        },
        onNewChat = {
            skipPartiallyExpanded = true
            openBottomSheet = ! openBottomSheet
        }
    )

    // Sheet content
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            modifier = Modifier.padding(top = 24.dp),
            scrimColor = Color.White.copy(0.32f)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("New Chat")
            }
            ContactContent(uiState = exampleContactUiState)
            Box(modifier = Modifier.height(16.dp))
        }
    }
}