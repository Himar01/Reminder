package com.example.reminder.data.model

import java.util.*

//@Entity(tableName = "task")
class TaskModel(
    var id: Int,
    var name: String,
    var description: String,
    var date: Int,
    var completed: Boolean
) {
    override fun toString(): String {
        return name
    }
}