package com.example.jetrider.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{riderId}")
    fun createRoute(riderId: String) = "home/$riderId"
}