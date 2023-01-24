package com.example.reminder.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.reminder.data.database.TaskDatabase
import com.example.reminder.data.database.dao.TaskDao
import com.example.reminder.data.model.TaskModel
import com.example.reminder.data.model.TaskProvider
import com.example.reminder.data.network.TaskService

class TaskRepository private constructor() {

    suspend fun getRandomTasks(): MutableList<TaskModel> {
        //Log.e("TaskRepository", "getRandomTasks ${this.toString()}")
        val response = api.getTasks()
        return response
    }

    /* TODO: Make access via Database */
//    suspend fun getRandomTasks(): MutableList<TaskModel> {
//        //Log.e("TaskRepository", "getRandomTasks ${this.toString()}")
//        val response = getTaskDao().getAllTasks()
//        TaskProvider.tasks = response.toMutableList()
//        return response
//    }

    companion object {
        @Volatile private var INSTANCE: TaskRepository? = null
        private val api = TaskService()
        private var database: TaskDatabase? = null

        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                database = buildDatabase(context = context)
                INSTANCE = TaskRepository()
            }
            return INSTANCE!!
        }
        private fun getTaskDao(): TaskDao {
            return database!!.getTaskDao()
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java, "Sample.db"
            ).build()
    }
}



