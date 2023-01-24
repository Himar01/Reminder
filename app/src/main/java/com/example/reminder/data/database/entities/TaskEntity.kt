package com.example.reminder.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = false )
    @ColumnInfo(name = "id")var id: Int = 0,
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "description")var description: String,
    @ColumnInfo(name = "date")var date: Int,
    @ColumnInfo(name = "quote_table")var completed: Boolean
)