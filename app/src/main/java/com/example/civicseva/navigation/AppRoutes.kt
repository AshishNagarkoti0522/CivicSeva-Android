package com.example.civicseva.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes {
    @Serializable
    data object SignUpRoute : AppRoutes

    @Serializable
    data object SignInRoute : AppRoutes

    @Serializable
    data object MainRoute : AppRoutes

    @Serializable
    data object HomeRoute : AppRoutes

    @Serializable
    data object ProfileRoute : AppRoutes

    @Serializable
    data object CreatePostRoute : AppRoutes
}