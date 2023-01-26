package com.example.reminder.data.model

import com.example.reminder.domain.model.Task
import java.util.*

//@Entity(tableName = "task")
class TaskModel(
    var id: Int = 0,
    var name: String,
    var description: String?,
    var date: Int?,
    var completed: Boolean
) {
    override fun toString(): String {
        return name
    }
}
fun Task.toModel() = TaskModel(name = name, description = description, date = date, completed = completed )