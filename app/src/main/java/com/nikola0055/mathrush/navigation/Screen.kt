package com.nikola0055.mathrush.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Game: Screen("gameScreen/{difficulty}/{time}")
}