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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Main activity for the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a NavHostController and load the navigation graph from XML
        navController = NavHostController(this@MainActivity)

        val navGraph: NavGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)

        val items = listOf(
            BottomNavigationItem("nav_home", Icons.Default.Home, NavGraphDestinations.Page1),
            BottomNavigationItem("nav_contact", Icons.Default.Search, NavGraphDestinations.Page2),
            BottomNavigationItem("nav_profile", Icons.Default.Person, NavGraphDestinations.Page3),
        )

        // Set up the activity's content view with a NavHost and a BottomNavigationBar
        setContent {
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
                            graph = navGraph,
                            modifier = Modifier.padding(innerPadding)
                        )
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

@ExperimentalMaterial3Api
@Composable
fun BottomNavigationDemo(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem("Page 1", Icons.Default.Home, NavGraphDestinations.Page1),
        BottomNavigationItem("Page 2", Icons.Default.Search, NavGraphDestinations.Page2),
        BottomNavigationItem("Page 3", Icons.Default.Person, NavGraphDestinations.Page3),
    )

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
            startDestination = NavGraphDestinations.Page1,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Add the composable functions for each fragment
            composable(NavGraphDestinations.Page1) { Page1() }
            composable(NavGraphDestinations.Page2) { Page2() }
            composable(NavGraphDestinations.Page3) { Page3() }
        }
    }
}


data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

object NavGraphDestinations {
    const val Page1 = "page1"
    const val Page2 = "page2"
    const val Page3 = "page3"
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
