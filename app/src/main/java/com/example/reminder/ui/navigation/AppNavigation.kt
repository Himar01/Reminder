
package com.example.reminder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminder.ui.screens.viewmodel.TaskViewModel
import com.example.reminder.ui.screens.TestScreen

@Composable
fun AppNavigation(viewModel: TaskViewModel){
    /*  It will be s    pread among all screens. It manages the navigating state between the screens.
        Keeps track of the back stack of composables that make up the screens in your app and the
        state of each screen.
    */
    val navController = rememberNavController()
    /*  The NavHost links the NavController with a navigation graph that specifies the composable
        destinations that should be able to navigate between.
    */
    NavHost(navController, startDestination = AppScreens.TestScreen.route){
        composable(AppScreens.TestScreen.route){
            TestScreen(viewModel, navController)
        }
    }
}