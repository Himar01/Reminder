package com.example.reminder.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.reminder.data.database.TaskDatabase
import com.example.reminder.data.database.dao.TaskDao
import com.example.reminder.data.database.entities.TaskEntity
import com.example.reminder.data.model.TaskModel
import com.example.reminder.data.network.TaskService
import com.example.reminder.domain.model.Task
import com.example.reminder.domain.model.toDomain

class TaskRepository private constructor() {

    suspend fun getRandomTasks(): MutableList<Task> {
        //Log.e("TaskRepository", "getRandomTasks ${this.toString()}")
        val response: List<TaskModel> = api.getTasks()
        return response.map { it.toDomain() }.toMutableList()
    }

    suspend fun getAllTasksFromDatabase(): MutableList<Task>{
        val response: List<TaskEntity> = getTaskDao().getAllTasks()
        return response.map { it.toDomain() }.toMutableList()
    }

    suspend fun insertTask(task: TaskEntity){
        getTaskDao().insert(task)
    }

    suspend fun deleteTask(task:TaskEntity){
        getTaskDao().delete(task)
    }

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



