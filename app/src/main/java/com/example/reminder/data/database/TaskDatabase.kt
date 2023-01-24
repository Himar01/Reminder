package com.example.reminder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminder.data.database.dao.TaskDao
import com.example.reminder.data.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
}