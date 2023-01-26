package com.example.reminder.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reminder.R
import com.example.reminder.domain.model.Task
import com.example.reminder.ui.screens.view.TaskDialog
import com.example.reminder.ui.screens.viewmodel.TaskViewModel
import com.example.reminder.ui.theme.ReminderTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    val taskListViewModel: List<Task>? by viewModel.taskList.observeAsState(initial = arrayListOf())
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
    val dialogShown: Boolean? by viewModel.dialogShown.observeAsState(initial = false)
    val selectedTask: Task? by viewModel.selectedTask.observeAsState(initial = null)

    val update: Boolean by viewModel.update.observeAsState(initial = false)

    val completedTaskVisible: Boolean by viewModel.completedTaskVisible.observeAsState(initial = false)

    if (dialogShown != null && dialogShown!!) {
        TaskDialog(
            taskViewModel = viewModel,
            task = selectedTask,
            onBackButtonClicked = { viewModel.showDialog(false) },
            onDeleteButtonClicked = {
                viewModel.deleteTask(it)
                viewModel.showDialog(false)
            },
            onConfirmButtonClicked = {
                viewModel.changeSelectedTask(it)
                viewModel.updateTask()
                viewModel.showDialog(false)
            })
    }
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
    if (update) {
        Text("")
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
//            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 3.dp, bottom = 15.dp)
        ) {
            itemsIndexed(items = taskListViewModel?.toList() ?: emptyList()) { index, task ->
                val context = LocalContext.current
                if (if (completedTaskVisible) task.completed else !task.completed) {
                    Box(
                        Modifier.padding(
                            bottom = 16.dp,
                            top = if (index == 0) 16.dp else 0.dp

                        )
                    ) {
                        TaskView(Modifier, task, onCardClicked = { task ->
                            viewModel.changeSelectedTask(task)
                            viewModel.showDialog(true)
                        }, onCompleteClicked = { task ->
                            task.completed = !task.completed
                            viewModel.updateTask(task)
                        }, completedTaskVisible)
                    }
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
            bottomMenu(onNewTaskClicked = {
                viewModel.changeSelectedTask(null)
                viewModel.showDialog(true)
            }, onCompletedTaskVisible = { viewModel.changeCompletedTaskVisibility() },
                completedTaskVisible
            )
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
fun bottomMenu(
    onNewTaskClicked: () -> Unit,
    onCompletedTaskVisible: () -> Unit,
    completedTaskVisible: Boolean
) {
    Button(
        onClick = { onCompletedTaskVisible() },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (completedTaskVisible) Color.Gray else colorResource(id = R.color.accomplishedTaskButtonBackground),
            contentColor = colorResource(id = R.color.accomplishedTaskButtonBackground),
        ),
    ) {
        Text(
            modifier = Modifier.padding(0.dp),
            text = stringResource(R.string.completedTasks),
            color = Color.White,

            )
    }
    menuBar { onNewTaskClicked() }
}


@Composable
fun menuBar(onNewTaskClicked: () -> Unit) {
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
        newTaskButton(modifier = Modifier, { onNewTaskClicked() })
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
fun newTaskButton(modifier: Modifier, onNewTaskClicked: () -> Unit) {
    Column() {
        Button(
            modifier = modifier
                .width(65.dp)
                .height(65.dp),
            onClick = { onNewTaskClicked() },
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
fun TaskView(
    modifier: Modifier = Modifier,
    task: Task = Task(0, "prueba", "descripción", 20221001, false),
    onCardClicked: (Task) -> Unit = {},
    onCompleteClicked: (Task) -> Unit = {},
    completedTaskVisible: Boolean = false
) {
    Card(
        backgroundColor = colorResource(id = R.color.taskBackground),
        shape = RoundedCornerShape(
            bottomStart = 25.dp, bottomEnd = 5.dp, topStart = 5.dp, topEnd = 5.dp
        ),
        elevation = 5.dp,
        modifier = Modifier
            .wrapContentHeight()
            .clickable { onCardClicked(task) },
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
                    Text(
                        fontSize = 20.sp, text = task.name ?: "", maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(8.dp))
                    var dateText = if (task.date != null) dateFormat(task.date!!) else ""
                    var past = false
                    if(dateText.substring(0,1)=="*"){
                        past = true
                        dateText = dateText.substring(1,dateText.length)
                    }
                    Text(
                        dateText,
                        modifier = Modifier.padding(start = 7.dp),
                        color = if(past) Color.Red else Color.Gray,
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
                if (completedTaskVisible) {
                    IconButton(
                        onClick = {
                            onCompleteClicked(task)
                        },
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_check_24),
                            "",
                            tint = colorResource(id = R.color.checkIcon),
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            onCompleteClicked(task)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(id = R.color.taskButtonPressed),
                            backgroundColor = if (completedTaskVisible) colorResource(id = R.color.taskButton) else Color.Transparent
                        ),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        border = BorderStroke(1.dp, colorResource(id = R.color.taskButton))
                    ) {}
                }

            }
            Spacer(Modifier.weight(.3f))

        }
    }
}

@Composable
fun dateFormat(date: Int): String {
    val dt: Date? = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(date.toString())
    val cal: Calendar = Calendar.getInstance()
    cal.time = dt!!
    // Results are tenth of a day
    return when (((cal.timeInMillis - Calendar.getInstance().timeInMillis) / 8640000)) {
        in -19..-10 -> "*Ayer"
        in -9..-1 -> "Hoy"
        in 0..9 -> "Mañana"
        in 9..Int.MAX_VALUE -> LocalDate.parse(
            date.toString(),
            DateTimeFormatter.ofPattern("yyyyMMdd")
        ).format(DateTimeFormatter.ofPattern("MMM, d yyyy"))
        else -> "*"+LocalDate.parse(
            date.toString(),
            DateTimeFormatter.ofPattern("yyyyMMdd")
        ).format(DateTimeFormatter.ofPattern("MMM, d yyyy"))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ReminderTheme() {
        val nav = rememberNavController()
        TestScreen(navController = nav, viewModel = TaskViewModel(LocalContext.current))
    }
}

