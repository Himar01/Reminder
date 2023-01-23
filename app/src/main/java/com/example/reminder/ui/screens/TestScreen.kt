package com.example.reminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reminder.R
import com.example.reminder.data.TaskModel
import com.example.reminder.ui.theme.ReminderTheme


@Composable
fun TestScreen(viewModel: TaskViewModel, navController: NavController) {
    viewModel.onCreate()
    Scaffold(topBar = {
        Toolbar()
    }) {
        BodyContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@Composable
fun Toolbar(){
    TopAppBar(
        title = { Text(text = "First Task Screen", color = colorResource(id = R.color.white)) },
        backgroundColor = colorResource(id = R.color.background)
    )
}

@Composable
fun BodyContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: TaskViewModel
) {

    val taskListViewModel: MutableList<TaskModel>? by viewModel.taskList.observeAsState(initial = null)

    Column(
        modifier = modifier.fillMaxSize().padding(all = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        taskListViewModel?.forEach { Task(Modifier, TaskModel(it.name, it.description, it.date)) }
        LazyColumn(modifier = Modifier.fillMaxSize()) {}
    }
}

@Composable
fun Task(
    modifier: Modifier = Modifier,
    task: TaskModel = TaskModel("prueba", "descripción", 20221001)
) {
    Card(backgroundColor = Color.LightGray) {
        Column(modifier.fillMaxWidth()) {
            Text(task.name?:"")
            Text(task.description?:"")
            Text(task.date.toString()?:"")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ReminderTheme() {
        val nav = rememberNavController()
        TestScreen(navController = nav, viewModel = TaskViewModel())
    }
}

