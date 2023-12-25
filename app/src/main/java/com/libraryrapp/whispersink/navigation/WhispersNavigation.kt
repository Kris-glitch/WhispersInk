package com.libraryrapp.whispersink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.libraryrapp.whispersink.screens.WhispersSplashScreen
import com.libraryrapp.whispersink.screens.home.HomeScreen
import com.libraryrapp.whispersink.screens.login.LoginScreen
import com.libraryrapp.whispersink.screens.rating.RatingsScreen
import com.libraryrapp.whispersink.screens.search.SearchScreen

@Composable
fun WhispersNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = WhispersScreens.WhispersSplashScreen.name ) {

        composable(WhispersScreens.WhispersSplashScreen.name) {
            WhispersSplashScreen(navController = navController)
        }
        composable(WhispersScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(WhispersScreens.RatingsScreen.name) {
            RatingsScreen(navController = navController)
        }

        composable(WhispersScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(WhispersScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

    }

}