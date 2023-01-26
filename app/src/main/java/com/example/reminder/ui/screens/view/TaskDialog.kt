package com.example.reminder.ui.screens.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskDialog(
    task: Task? = null,
    onBackButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: (Task?) -> Unit = {},
    onConfirmButtonClicked: (Task) -> Unit = {}
) {
    var newTask = task
    var title: String by remember { mutableStateOf(task?.name ?: "") }
    var description: String by remember { mutableStateOf(task?.description ?: "") }

    /* TODO Añadir un nuevo dialogo de confirmación con botones SI NO */
    Dialog(
        onDismissRequest = { onBackButtonClicked() }) {
        Surface(
            shape = RoundedCornerShape(
                bottomStart = 10.dp, bottomEnd = 10.dp, topStart = 10.dp, topEnd = 10.dp
            ),
        ) {
            Column {
                // TOP MENU ICONS
                TopMenu(onBackButtonClicked,
                    {
                        onDeleteButtonClicked(task)
                    },
                    {
                        if(task==null){
                            newTask = Task(0, title, description, null, false)
                        }else {
                            newTask = Task(task.id,title, description, null, false)
                        }
                        onConfirmButtonClicked(newTask!!)
                    }, onRandomButtonClicked = {

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
                Options()
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
                        Text("Escribe el título", color = Color.LightGray, fontSize = 16.sp)
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun Options() {
    val context = LocalContext.current
    Column {
        Row(
            Modifier.padding(start = 15.dp, top = 20.dp, bottom = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_intro),
                fontSize = 18.sp,
            )
            TextButton(
                onClick = {
                    Toast.makeText(context, "Choose date", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.taskButtonPressed)
                ),
            ) {
                Text(
                    "Choose", color = colorResource(id = R.color.taskButton),
                    textAlign = TextAlign.Center,
                )
            }
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
                .fillMaxWidth(),
            maxLines = 5,
            textStyle = TextStyle(
                color = Color.Gray,
                textAlign = TextAlign.Justify,
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
        IconButton(onClick = { onRandomButtonClicked() }, modifier = Modifier.padding(end = 5.dp)) {
            Icon(
                painterResource(id = R.drawable.random),
                "",
                tint = Color.LightGray,
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
