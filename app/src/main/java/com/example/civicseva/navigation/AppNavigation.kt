package com.example.civicseva.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.civicseva.data.ApiService
import com.example.civicseva.data.UserPreferencesRepository
import com.example.civicseva.screen.HomeScreen
import com.example.civicseva.screen.SignInScreen
import com.example.civicseva.screen.SignUpScreen
import com.example.civicseva.viewmodel.SignInVM
import com.example.civicseva.viewmodel.SignUpVM

@Composable
fun AppNavigation(
    apiService: ApiService,
    repository: UserPreferencesRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SignUpRoute) {
        composable<SignUpRoute> {
            @Suppress("UNCHECKED_CAST")
            val signUpFactory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SignUpVM(apiService, repository) as T
                }
            }

            val signUpVM: SignUpVM = viewModel(factory = signUpFactory)

            SignUpScreen(
                vm = signUpVM,
                onSignInClick = {
                    navController.navigateSafe(SignInRoute) {
                        popUpTo(SignUpRoute) { inclusive = true }
                    }
                },
                onAuthSuccess = {
                    navController.navigateSafe(HomeRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<SignInRoute> {
            @Suppress("UNCHECKED_CAST")
            val signInFactory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SignInVM(apiService, repository) as T
                }
            }

            val signInVM: SignInVM = viewModel(factory = signInFactory)

            SignInScreen(
                vm = signInVM,
                onSignUpClick = {
                    navController.navigateSafe(SignUpRoute) {
                        popUpTo(SignInRoute) { inclusive = true }
                    }
                },
                onAuthSuccess = {
                    navController.navigateSafe(HomeRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            HomeScreen()
        }
    }
}

fun NavHostController.navigateSafe(
    route: Any,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route) {
            launchSingleTop = true // Double click se bachane ke liye default
            builder() // Custom rules (jaise popUpTo) yahan inject ho jayenge
        }
    }
}