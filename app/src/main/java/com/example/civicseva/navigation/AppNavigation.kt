package com.example.civicseva.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.civicseva.features.main.MainScreen
import com.example.civicseva.features.signin.SignInScreen
import com.example.civicseva.features.signup.SignUpScreen
import com.example.civicseva.features.signin.SignInVM
import com.example.civicseva.features.signup.SignUpVM

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SignUpRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable<AppRoutes.SignUpRoute> {
            val signUpVM: SignUpVM = hiltViewModel()

            SignUpScreen(
                vm = signUpVM,
                onSignInClick = {
                    navController.navigateSafe(AppRoutes.SignInRoute) {
                        popUpTo(AppRoutes.SignUpRoute) { inclusive = true }
                    }
                },
                onAuthSuccess = {
                    navController.navigateSafe(AppRoutes.MainRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<AppRoutes.SignInRoute> {
            val signInVM: SignInVM = hiltViewModel()

            SignInScreen(
                vm = signInVM,
                onSignUpClick = {
                    navController.navigateSafe(AppRoutes.SignUpRoute) {
                        popUpTo(AppRoutes.SignInRoute) { inclusive = true }
                    }
                },
                onAuthSuccess = {
                    navController.navigateSafe(AppRoutes.MainRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<AppRoutes.MainRoute> {
            MainScreen()
        }
    }
}

fun NavHostController.navigateSafe(
    route: Any,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route) {
            launchSingleTop = true
            builder()
        }
    }
}

fun NavHostController.popBackStackSafe() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}