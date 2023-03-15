/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetchat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.jetchat.callhistory.CallHistoryContent
import com.example.compose.jetchat.callhistory.CallHistoryScreen
import com.example.compose.jetchat.contact.ContactFragment
import com.example.compose.jetchat.contact.ContactScreen
import com.example.compose.jetchat.conversation.ConversationScreen
import com.example.compose.jetchat.profile.ProfileContent
import com.example.compose.jetchat.profile.ProfileScreen
import com.example.compose.jetchat.recentchat.RecentCallScreen

/**
 * Main activity for the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = listOf(
            BottomNavigationItem("Home", Icons.Default.Home, NavGraphDestinations.RecentChat),
            BottomNavigationItem("Contact", Icons.Default.Search, NavGraphDestinations.Contact),
            BottomNavigationItem("Call history", Icons.Default.Call, NavGraphDestinations.CallHistory),
        )

        // Set up the activity's content view with a NavHost and a BottomNavigationBar
        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                items = items,
                            )
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = NavGraphDestinations.RecentChat,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(NavGraphDestinations.Home) { ConversationScreen(navController) }
                            composable(NavGraphDestinations.Profile + "/{userId}") { backStackEntry ->
                                ProfileScreen(navController, backStackEntry.arguments?.getString("userId"))
                            }
                            composable(NavGraphDestinations.CallHistory) { CallHistoryScreen(navController) }
                            composable(NavGraphDestinations.Contact) { ContactScreen(navController) }
                            composable(NavGraphDestinations.RecentChat) { RecentCallScreen(navController) }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    val selectedIndex = remember { mutableStateOf(0) }

    NavigationBar(
        modifier = modifier.background(backgroundColor),
        contentColor = contentColor,
        tonalElevation = 8.dp,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(item.route)
                },
            )
        }
    }
}

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

object NavGraphDestinations {
    const val Home = "home"
    const val Profile = "profile"
    const val ChatHistory = "chat_history"
    const val Contact = "contact"
    const val CallHistory = "chat_history"
    const val RecentChat = "recent_chat"
}

@Composable
fun Page1() {
    // Composable function for Page 1
}

@Composable
fun Page2() {
    // Composable function for Page 2
}

@Composable
fun Page3() {
    // Composable function for Page 3
}
