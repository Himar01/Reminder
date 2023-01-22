package com.example.reminder.data

import java.util.*

@Entity(tableName = "task")
class Task {
    var name: String? = null
    var description: String? = null
    var date: Date? = null
    

    override fun toString(): String {
        return name ?: ""
    }
}