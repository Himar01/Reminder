package com.example.reminder.domain.model

data class Task(
    var id: Int,
    var name: String,
    var description: String,
    var date: Int,
    var completed: Boolean
) {
}