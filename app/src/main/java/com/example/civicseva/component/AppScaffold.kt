package com.example.civicseva.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// Your main reusable Scaffold component
@ExperimentalMaterial3Api
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBarTitle: @Composable (TopAppBarScrollBehavior) -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}, // 🔥 Naya: Bottom Navigation ke liye
    snackbarHost: @Composable () -> Unit = {}, // 🔥 Naya: Messages dikhane ke liye
    containerColor: Color = MaterialTheme.colorScheme.background, // 🔥 Naya: Pure black override ke liye
    content: @Composable (PaddingValues) -> Unit
) {
    // 1. Scroll Engine
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        // 🔥 FIX: Fully qualified names hata diye, direct extension functions use kiye
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { topBarTitle(scrollBehavior) },
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        containerColor = containerColor
    ) { innerPadding ->
        // 🔥 Box hata diya taaki double padding na ho aur edge-to-edge sahi se kaam kare
        content(innerPadding)
    }
}

// Demo/Boilerplate for visualization
@ExperimentalMaterial3Api
@Preview(showBackground = true, name = "App Scaffold Demo")
@Composable
fun AppScaffoldDemo() {
    // Snackbar state manage karne ke liye
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AppScaffold(
        topBarTitle = { },
        onBackClick = { /* Handle Back */ },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 🔥 FIX: Sahi coroutine launch syntax
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Action Clicked!")
                    }
                }
            ) {
                Icon(
                    // 🔥 FIX: Icons default 'Add' import kar diya upar
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        bottomBar = {
            NavigationBar {
                // 🔥 FIX: Fully qualified name hata kar direct use kiya
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { AppText("Home") }
                )
            }
        },
        // 🔥 Agar true pitch-black AMOLED look chahiye toh:
        // containerColor = Color.Black
    ) { innerPadding ->

        // Asli layout yahan banega jisme innerPadding pass hogi
        LazyColumn(
            contentPadding = innerPadding,
            // 🔥 FIX: fillMaxSize() aur padding ab sahi se imported hain
            modifier = Modifier.fillMaxSize()
        ) {
            items(20) { index ->
                AppText(
                    text = "Item number $index",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}