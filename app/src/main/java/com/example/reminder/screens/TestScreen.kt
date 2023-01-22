package com.example.reminder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reminder.ui.theme.ReminderTheme

@Composable
fun TestScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar() {
        }
    }) {
        BodyContent(navController)
    }
}


@Composable
fun BodyContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola navegación")
        LazyColumn(modifier = Modifier.fillMaxSize() ) {
            item(){
                Text("FirstText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }
            item(){
                Text("SecondText")
            }



        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ReminderTheme() {
        val nav = rememberNavController()
        TestScreen(navController = nav)
    }
}

