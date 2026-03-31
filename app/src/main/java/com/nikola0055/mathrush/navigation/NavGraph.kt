package com.nikola0055.mathrush.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikola0055.mathrush.ui.screen.GameScreen
import com.nikola0055.mathrush.ui.screen.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.Game.route) {
            GameScreen(
                navController = navController,
                difficulty = it.arguments?.getString("difficulty")!!,
                time = it.arguments?.getString("time")!!
            )
        }
    }
}