package com.fathoor.cityzen.presentation.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("home/{playerId}") {
        fun createRoute(playerId: Int) = "home/$playerId"
    }
    object Highlight: Screen("highlight")
    object Profile: Screen("profile")
}