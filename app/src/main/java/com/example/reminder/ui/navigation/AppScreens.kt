package com.example.reminder.ui.navigation

/** Kind of a enum, but with steroids. Allows us to have multiple classes inside another  **/
sealed class AppScreens(val route: String) {
    object TestScreen: AppScreens("test_screen")
}
