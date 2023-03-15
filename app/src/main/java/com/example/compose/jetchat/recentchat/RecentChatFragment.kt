package com.example.compose.jetchat.recentchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.compose.jetchat.NavGraphDestinations
import com.example.compose.jetchat.R
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

@Composable
fun RecentCallScreen(navController: NavController) {
    RecentChatContent(
        exampleRecentChatUiState,
        onOpenConversation = {
            navController.navigate(NavGraphDestinations.Home)
        }
    )
}