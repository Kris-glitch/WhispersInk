package com.libraryrapp.whispersink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.libraryrapp.whispersink.screens.WhispersSplashScreen
import com.libraryrapp.whispersink.screens.details.BookDetailsScreen
import com.libraryrapp.whispersink.screens.home.HomeScreen
import com.libraryrapp.whispersink.screens.login.LoginScreen
import com.libraryrapp.whispersink.screens.rating.RatingsScreen
import com.libraryrapp.whispersink.screens.settings.SettingsScreen

@Composable
fun WhispersNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = WhispersScreens.WhispersSplashScreen.route) {

        composable(WhispersScreens.WhispersSplashScreen.route) {
            WhispersSplashScreen(navController = navController)
        }
        composable(WhispersScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(WhispersScreens.RatingsScreen.route) {
            RatingsScreen(navController = navController)
        }

        composable(WhispersScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(WhispersScreens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable("${WhispersScreens.BookDetailsScreen.route}/{bookId}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val bookId = requireNotNull(arguments.getString("bookId"))
            BookDetailsScreen(navController = navController, bookId = bookId)
        }


    }

}