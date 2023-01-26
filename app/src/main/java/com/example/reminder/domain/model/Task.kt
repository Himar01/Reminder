package com.example.reminder.domain.model

import com.example.reminder.data.database.entities.TaskEntity
import com.example.reminder.data.model.TaskModel

data class Task(
    var id: Int,
    var name: String,
    var description: String?,
    var date: Int?,
    var completed: Boolean
)

fun TaskModel.toDomain() = Task(id,name,description,date,completed)
fun TaskEntity.toDomain() = Task(id,name,description,date,completed)