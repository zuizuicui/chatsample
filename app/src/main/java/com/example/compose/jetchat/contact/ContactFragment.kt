package com.example.compose.jetchat.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.compose.jetchat.data.exampleContactUiState
import com.example.compose.jetchat.theme.JetchatTheme

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setContent {
            JetchatTheme {
                ContactContent(
                    exampleContactUiState,
                )
            }
        }
    }
}

@Composable
fun ContactScreen(navController: NavController) {
    JetchatTheme {
        ContactContent(
            exampleContactUiState,
        )
    }
}