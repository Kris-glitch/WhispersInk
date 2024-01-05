package com.libraryrapp.whispersink.navigation

enum class WhispersScreens {
    WhispersSplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    BookDetailsScreen,
    SettingsScreen,
    UpdateScreen,
    RatingsScreen;

    companion object {
        fun fromRoute(route: String?): WhispersScreens
                = when(route?.substringBefore("/")) {
            WhispersSplashScreen.name -> WhispersSplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            HomeScreen.name -> HomeScreen
            BookDetailsScreen.name -> BookDetailsScreen
            SettingsScreen.name -> SettingsScreen
            UpdateScreen.name -> UpdateScreen
            RatingsScreen.name -> RatingsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}