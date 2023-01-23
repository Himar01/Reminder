package com.example.reminder.data

import java.util.*

//@Entity(tableName = "task")
class TaskModel(
    val name: String,
    val description: String,
    val date: Int
) {
    override fun toString(): String {
        return name
    }
}