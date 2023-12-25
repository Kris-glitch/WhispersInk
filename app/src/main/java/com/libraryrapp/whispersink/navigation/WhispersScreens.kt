package com.libraryrapp.whispersink.navigation

enum class WhispersScreens {
    WhispersSplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    BookDetailsScreen,
    UpdateScreen,
    RatingsScreen;

    companion object {
        fun fromRoute(route: String?): WhispersScreens
                = when(route?.substringBefore("/")) {
            WhispersSplashScreen.name -> WhispersSplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            BookDetailsScreen.name -> BookDetailsScreen
            UpdateScreen.name -> UpdateScreen
            RatingsScreen.name -> RatingsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}