package com.example.reminder.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reminder.R
import com.example.reminder.data.TaskModel
import com.example.reminder.ui.theme.ReminderTheme
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun TestScreen(viewModel: TaskViewModel, navController: NavController) {
    viewModel.onCreate()
    Scaffold() {
        BodyContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@Composable
fun Toolbar() {
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
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp)
            /** ERASE **/
            .border(BorderStroke(0.dp, Color.Black)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(100.dp))
        taskListViewModel?.forEach { Task(Modifier, TaskModel(it.name, it.description, it.date)) }
        Spacer(Modifier.fillMaxHeight())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Task(
    modifier: Modifier = Modifier,
    task: TaskModel = TaskModel("prueba", "descripción", 20221001)
) {
    Card(
        backgroundColor = colorResource(id = R.color.taskBackground),
        shape = RoundedCornerShape(
            bottomStart = 25.dp,
            bottomEnd = 5.dp,
            topStart = 5.dp,
            topEnd = 5.dp
        ),
        elevation = 5.dp,
        modifier = Modifier
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                /** ERASE **/
                .border(BorderStroke(0.dp, Color.Black)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier
                    .weight(2f)
                    .padding(all = 15.dp)
                    /** ERASE **/
                    .border(BorderStroke(0.dp, Color.Black))
            ) {
                ProvideTextStyle(TextStyle(color = Color.Black)) {
                    Text(fontSize = 22.sp, text = task.name ?: "")
                    Text(
                        dateFormat(task.date) ?: "",
                        modifier = Modifier.padding(start = 7.dp),
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.taskButtonPressed)
                ),
                modifier = Modifier
                    .sizeIn(maxWidth = 5.dp, maxHeight = 5.dp)
                    .weight(1f)
                    .aspectRatio(1f)

                    /** ERASE **/

                    .border(BorderStroke(0.dp, Color.Black)),
                border = BorderStroke(1.dp, colorResource(id = R.color.taskButton))
            ) {
            }
        }
    }
}

fun dateFormat(date: Int): String{
    val dt :Date? = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(date.toString())
    val cal: Calendar = Calendar.getInstance()
    cal.time = dt!!
    val format = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    return format.format(cal.time)
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ReminderTheme() {
        val nav = rememberNavController()
        TestScreen(navController = nav, viewModel = TaskViewModel())
    }
}

