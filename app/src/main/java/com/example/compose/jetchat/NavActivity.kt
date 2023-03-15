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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.compose.jetchat.components.JetchatDrawer
import com.example.compose.jetchat.conversation.BackPressHandler
import com.example.compose.jetchat.conversation.LocalBackPressedDispatcher
import com.example.compose.jetchat.databinding.ContentMainBinding
import com.google.android.material.color.utilities.MaterialDynamicColors.primary
import kotlinx.coroutines.launch

/**
 * Main activity for the app.
 */
class NavActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(

            ComposeView(this).apply {
                consumeWindowInsets = false
                setContent {
                    CompositionLocalProvider(
                        LocalBackPressedDispatcher provides this@NavActivity.onBackPressedDispatcher
                    ) {
                        val drawerState = rememberDrawerState(initialValue = Closed)
                        val drawerOpen by viewModel.drawerShouldBeOpened
                            .collectAsStateWithLifecycle()

                        if (drawerOpen) {
                            // Open drawer and reset state in VM.
                            LaunchedEffect(Unit) {
                                // wrap in try-finally to handle interruption whiles opening drawer
                                try {
                                    drawerState.open()
                                } finally {
                                    viewModel.resetOpenDrawerAction()
                                }
                            }
                        }

                        // Intercepts back navigation when the drawer is open
                        val scope = rememberCoroutineScope()
                        if (drawerState.isOpen) {
                            BackPressHandler {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }

                        JetchatDrawer(
                            drawerState = drawerState,
                            onChatClicked = {
                                findNavController().popBackStack(R.id.nav_home, false)
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            onProfileClicked = {
                                val bundle = bundleOf("userId" to it)
                                findNavController().navigate(R.id.nav_profile, bundle)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        ) {
                            Scaffold(
                                bottomBar = {
                                    BottomBar()
                                }
                            ) {
                                AndroidViewBinding(
                                    ContentMainBinding::inflate,
                                    modifier = Modifier.padding(it)
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Create",
            route = "add",
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
    )

    @Composable
    private fun BottomBar() {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            bottomNavItems.forEach { item ->
                val selected = item.route == "home"

                NavigationBarItem(
                    selected = selected,
                    onClick = { findNavController().navigate(item.route) },
                    label = {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "${item.name} Icon",
                        )
                    }
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    data class BottomNavItem(
        val name: String,
        val route: String,
        val icon: ImageVector,
        val page: Int = R.id.nav_host_fragment
    )
}
