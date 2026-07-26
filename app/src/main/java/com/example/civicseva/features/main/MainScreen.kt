@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.civicseva.features.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.civicseva.core.component.AppScaffold
import com.example.civicseva.core.component.BottomNavItem
import com.example.civicseva.core.component.FloatingBottomNav
import com.example.civicseva.features.createpost.CreatePostScreen
import com.example.civicseva.features.home.HomeScreen
import com.example.civicseva.features.profile.ProfileScreen
import com.example.civicseva.navigation.AppRoutes

@Composable
fun MainScreen(
    vm: MainVM = hiltViewModel()
) {
    val tabNavController = rememberNavController()
    val navItems = listOf(
        BottomNavItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = AppRoutes.HomeRoute
        ),
        BottomNavItem(
            label = "Create Post",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            route = AppRoutes.CreatePostRoute
        ),
        BottomNavItem(
            label = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = AppRoutes.ProfileRoute
        )
    )

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AppScaffold(
        disableTopBarScroll = true,
        bottomBar = {
            FloatingBottomNav(
                items = navItems,
                selectedIndex = navItems.indexOfFirst {
                    currentDestination?.hasRoute(it.route::class) == true
                }.coerceAtLeast(0),
                onItemSelected = {
                    val selectedRoutes = navItems[it].route

                    tabNavController.navigate(selectedRoutes) {
                        popUpTo(tabNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = AppRoutes.HomeRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<AppRoutes.HomeRoute> {
                HomeScreen()
            }
            composable<AppRoutes.CreatePostRoute> {
                CreatePostScreen()
            }
            composable<AppRoutes.ProfileRoute> {
                ProfileScreen()
            }
        }
    }
}