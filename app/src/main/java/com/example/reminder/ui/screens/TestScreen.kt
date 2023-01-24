package com.example.reminder.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reminder.R
import com.example.reminder.data.model.TaskModel
import com.example.reminder.ui.theme.ReminderTheme
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun TestScreen(viewModel: TaskViewModel, navController: NavController) {
    viewModel.onCreate()
    Scaffold() {
        BodyContent(
            modifier = Modifier
                .padding(it), navController, viewModel
        )
    }
}

@Composable
fun Toolbar() {

}

@Composable
fun BodyContent(
    modifier: Modifier, navController: NavController, viewModel: TaskViewModel
) {

    val taskListViewModel: MutableList<TaskModel>? by viewModel.taskList.observeAsState(initial = arrayListOf())
    val mindTextStyle: TextStyle by viewModel.mindTextStyle.observeAsState(
        initial = TextStyle(
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 20.sp
        )
    )
    val taskTextStyle: TextStyle by viewModel.taskTextStyle.observeAsState(
        initial = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    )
    val backgroundOn: Boolean by viewModel.backgroundOn.observeAsState(initial = false)

    @Composable
    fun Modifier.viewModelPaint(): Modifier {
        return if (backgroundOn) {
            this.paint(
                painterResource(id = R.drawable.subway_whiter),
                contentScale = ContentScale.Crop
            )
        } else {
            this
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .viewModelPaint(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TopBar(mindTextStyle, taskTextStyle) { viewModel.onScreenChanged(it) }
        }
        // All Tasks 
        LazyColumn(
            Modifier
                .weight(3f)
                .padding(top = 10.dp, start = 25.dp, end = 25.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(top = 3.dp, bottom = 15.dp)
        ) {
            items(items = taskListViewModel?.toList() ?: emptyList()) {
                if (!it.completed) {
                    Task(Modifier, it)
                }
            }
        }
        // Botton Menu
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            bottomMenu()
        }
    }
}

@Composable
fun RowScope.TopBar(
    mindTextStyle: TextStyle,
    taskTextStyle: TextStyle,
    onClicked: (Boolean) -> Unit
) {
    Spacer(Modifier.width(20.dp))
    ClickableText(
        text = AnnotatedString(stringResource(R.string.Mind)),
        style = mindTextStyle,
        modifier = Modifier
            .weight(1f),
        onClick = {
            onClicked(true)
        },
    )
    ClickableText(
        AnnotatedString(stringResource(R.string.Tasks)),
        style = taskTextStyle,
        modifier = Modifier
            .weight(1f),
        onClick = {
            onClicked(false)
        }
    )
    Spacer(Modifier.width(20.dp))
}

@Composable
fun bottomMenu() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = colorResource(id = R.color.accomplishedTaskButtonBackground)
        ),
    ) {
        Text(
            modifier = Modifier.padding(0.dp),
            text = stringResource(R.string.accomplishedTasks),
            color = Color.White
        )
    }
    menuBar()
}

@Preview
@Composable
fun menuBar() {
    Row(
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.submenu_background),
                contentScale = ContentScale.FillBounds
            )
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Button
        MenuButton()
        Spacer(modifier = Modifier.weight(1f))
        newTaskButton(modifier = Modifier)
        Spacer(modifier = Modifier.weight(1f))
        // Config Icon Button
        ConfigButton()
    }

}

@Composable
fun ConfigButton() {
    Column() {
        Spacer(modifier = Modifier.height(35.dp))
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier
        ) {
            Icon(
                Icons.Default.MoreVert,
                "",
                tint = colorResource(id = R.color.menuIcons),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun MenuButton() {
    Column() {
        Spacer(modifier = Modifier.height(35.dp))
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier
        ) {
            Icon(
                Icons.Default.Menu,
                "",
                tint = colorResource(id = R.color.menuIcons),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun newTaskButton(modifier: Modifier) {
    Column() {
        Button(
            modifier = modifier
                .width(65.dp)
                .height(65.dp),
            onClick = { /*TODO*/ },
            shape = CircleShape,
            contentPadding = PaddingValues(all = 7.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.newTaskButton),
                contentColor = colorResource(id = R.color.white)
            ),
        ) {
            Icon(
                painterResource(id = R.drawable.add),
                "",
                modifier = Modifier.fillMaxSize(),
                tint = colorResource(id = R.color.newTaskButtonAdd)
            )
        }
        Spacer(Modifier.height(38.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Task(
    modifier: Modifier = Modifier,
    task: TaskModel = TaskModel(0, "prueba", "descripción", 20221001, false)
) {
    Card(
        backgroundColor = colorResource(id = R.color.taskBackground),
        shape = RoundedCornerShape(
            bottomStart = 25.dp, bottomEnd = 5.dp, topStart = 5.dp, topEnd = 5.dp
        ),
        elevation = 5.dp,
        modifier = Modifier.wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier
                    .weight(5f)
                    .padding(start = 20.dp, top = 15.dp, end = 0.dp, bottom = 15.dp)

            ) {
                ProvideTextStyle(TextStyle(color = Color.Black)) {
                    /* TODO: Show ... when maxLine is applied */
                    Text(
                        fontSize = 20.sp, text = task.name ?: "", maxLines = 1
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        //dateFormat(task.date) ?: "",
                        "Falta 1 semana",
                        modifier = Modifier.padding(start = 7.dp),
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp)
                    .absolutePadding(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { task.completed = false },
                    shape = CircleShape,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colorResource(id = R.color.taskButtonPressed)
                    ),
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    border = BorderStroke(1.dp, colorResource(id = R.color.taskButton))
                ) {}
            }
            Spacer(Modifier.weight(.3f))

        }
    }
}

fun dateFormat(date: Int): String {
    val dt: Date? = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(date.toString())
    val cal: Calendar = Calendar.getInstance()
    cal.time = dt!!

    val format = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    // Tenth of a day
    /* TODO: Waiting for ROOM Database creation */
    return ((cal.timeInMillis - Calendar.getInstance().timeInMillis) / 8640000).toString()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ReminderTheme() {
        val nav = rememberNavController()
        TestScreen(navController = nav, viewModel = TaskViewModel(LocalContext.current))
    }
}

