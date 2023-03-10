package com.example.reminder.ui.screens.view


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.reminder.R
import com.example.reminder.domain.model.Task
import com.example.reminder.ui.screens.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TaskDialog(
    taskViewModel: TaskViewModel,
    task: Task? = null,
    onBackButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: (Task?) -> Unit = {},
    onConfirmButtonClicked: (Task) -> Unit = {}
) {
    var newTask = task
    var title: String by remember { mutableStateOf(task?.name ?: "") }
    var description: String by remember { mutableStateOf(task?.description ?: "") }
    var date: Int? = task?.date
    val isLoading: Boolean by taskViewModel.isLoading.observeAsState(false)
    var openDialog: Boolean by remember {
        mutableStateOf(false)
    }
    /* TODO Añadir un nuevo dialogo de confirmación con botones SI NO */
    Dialog(
        onDismissRequest = { onBackButtonClicked() }) {
        Surface(
            shape = RoundedCornerShape(
                bottomStart = 10.dp, bottomEnd = 10.dp, topStart = 10.dp, topEnd = 10.dp
            ),
        ) {
            if (openDialog) {
                Box() {
                    AlertDialog(onDismissRequest = { }, confirmButton = {
                        TextButton(
                            onClick = { openDialog = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = colorResource(id = R.color.newTaskButton)
                            )
                        ) {
                            Text(
                                stringResource(R.string.OK),
                                color = colorResource(id = R.color.taskButton)

                            )
                        }
                    },
                        title = { Text(text = stringResource(R.string.no_internet)) },
                        text = { Text(text = stringResource(R.string.no_internet_description)) })


                }
            }

            Column {
                // TOP MENU ICONS
                TopMenu(isLoading = isLoading, onBackButtonClicked = onBackButtonClicked,
                    onDeleteButtonClicked = {
                        onDeleteButtonClicked(task)
                    },
                    onConfirmButtonClicked = {
                        newTask = if (task == null) {
                            Task(0, title, description, date, false)
                        } else {
                            Task(task.id, title, description, date, false)
                        }
                        onConfirmButtonClicked(newTask!!)
                    }, onRandomButtonClicked = {
                        taskViewModel.setRandomTask { randomTask ->
                            if (randomTask != null) {
                                title = randomTask.name
                                description = randomTask.description ?: ""
                            } else {
                                openDialog = true
                            }
                        }
                    })
                Spacer(
                    Modifier
                        .height(20.dp)
                        .background(colorResource(id = R.color.DialogSpacer))
                        .fillMaxWidth()
                )
                // TITLE
                Title(title) { title = it }
                Spacer(
                    Modifier
                        .height(20.dp)
                        .background(colorResource(id = R.color.DialogSpacer))
                        .fillMaxWidth()
                )
                // OPTIONS
                Options(date = date, onDateChanged = { newDate ->
                    date = newDate?.toInt()
                })
                Spacer(
                    Modifier
                        .height(20.dp)
                        .background(colorResource(id = R.color.DialogSpacer))
                        .fillMaxWidth()
                )
                // DESCRIPTION
                Description(description) { description = it }
            }

        }

    }
}

@Composable
fun Title(titleValue: String, onTitleChanged: (String) -> Unit) {
    Column {
        Text(
            stringResource(R.string.title_intro),
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 0.dp, top = 5.dp),
            fontSize = 18.sp
        )
        /* TODO: Change focus when launched */
        BasicTextField(
            value = titleValue,
            onValueChange = { onTitleChanged(it) },
            modifier = Modifier
                .padding(start = 25.dp, end = 35.dp, bottom = 15.dp, top = 5.dp)
                .fillMaxWidth(),
            maxLines = 3,
            textStyle = TextStyle(
                color = Color.Gray,
                textAlign = TextAlign.Justify,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (titleValue == "") {
                        Text(
                            stringResource(R.string.write_title_hint),
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Options(onDateChanged: (String?) -> Unit, date: Int?) {
    val context = LocalContext.current

    Column {

        Row(
            Modifier.padding(start = 15.dp, top = 20.dp, bottom = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_intro),
                fontSize = 16.sp,
            )
            DatePicker(value = date?.toString(),
                onValueChange = { date -> onDateChanged(date) })
        }
    }
}

@Composable
fun Description(descriptionValue: String, onDescriptionChanged: (String) -> Unit) {
    Column {
        Text(
            stringResource(R.string.description_intro),
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 0.dp, top = 5.dp),
            fontSize = 18.sp
        )
        BasicTextField(
            value = descriptionValue,
            onValueChange = { onDescriptionChanged(it) },
            modifier = Modifier
                .padding(start = 25.dp, end = 35.dp, bottom = 15.dp, top = 5.dp)
                .fillMaxWidth()
                .height(100.dp),
            maxLines = 5,
            textStyle = TextStyle(
                color = Color.Gray,
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (descriptionValue == "") {
                        Text(stringResource(R.string.description_hint), color = Color.LightGray)
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun TopMenu(
    isLoading: Boolean,
    onBackButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {},
    onConfirmButtonClicked: () -> Unit = {},
    onRandomButtonClicked: () -> Unit = {},
) {
    Row(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackButtonClicked() }) {
            Icon(
                painterResource(id = R.drawable.backarrow),
                "",
                tint = colorResource(id = R.color.backIcon),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (!isLoading) {
            IconButton(
                onClick = { onRandomButtonClicked() },
                modifier = Modifier.padding(end = 5.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.random),
                    "",
                    tint = Color.LightGray,
                )
            }
        } else {
            CircularProgressIndicator(
                Modifier
                    .padding(end = 18.dp)
                    .width(20.dp)
                    .height(20.dp),
                color = colorResource(id = R.color.newTaskButton)
            )
        }

        IconButton(onClick = { onDeleteButtonClicked() }, modifier = Modifier.padding(end = 5.dp)) {
            Icon(
                painterResource(id = R.drawable.baseline_delete_24),
                "",
                tint = colorResource(id = R.color.deleteIcon),
            )
        }
        IconButton(onClick = { onConfirmButtonClicked() }) {
            Icon(
                painterResource(id = R.drawable.baseline_check_24),
                "",
                tint = colorResource(id = R.color.checkIcon),
            )
        }
    }
}

@Composable
fun DatePicker(
    value: String? = null,
    onValueChange: (String?) -> Unit,
    pattern: String = "yyyyMMdd",
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val displayedFormatter = DateTimeFormatter.ofPattern("MMM, d yyyy")
    val date =
        if (value?.isNotBlank() == true) LocalDate.parse(value, formatter) else LocalDate.now()
    var text by remember {
        mutableStateOf(
            if (value == null) "Elegir" else date.format(
                displayedFormatter
            )
        )
    }
    val dialog = DatePickerDialog(
        LocalContext.current,
        /* TODO Add proper colors to the datePicker */
//        R.style.App_Dialog_DateTime,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).format(formatter))
            text = LocalDate.of(year, month + 1, dayOfMonth).format(displayedFormatter)
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )
    TextButton(
        onClick = {
            dialog.show()
        },
        modifier = Modifier
            .padding(
                start = if (text == "Elegir") 25.dp else 10.dp,
                end = if (text == "Elegir") 25.dp else 0.dp
            ),
        colors = ButtonDefaults.textButtonColors(
            contentColor = colorResource(id = R.color.taskButtonPressed)
        ),
    ) {
        Text(
            text, color = colorResource(id = R.color.taskButton),
            textAlign = TextAlign.Center,
            fontSize = if (text == "Elegir") 16.sp else 12.sp
        )
    }
    if (text != "Elegir") {
        IconButton(onClick = {
            text = "Elegir"
            onValueChange(null)
        }) {
            Icon(
                painterResource(id = R.drawable.baseline_clear_circle),
                "",
                tint = Color.LightGray,
                modifier = Modifier.rotate(45f)
            )
        }
    }
}

