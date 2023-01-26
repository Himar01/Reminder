package com.example.reminder.data.network

import com.example.reminder.core.RetrofitHelper
import com.example.reminder.data.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getTasks(): List<TaskModel>{
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(TaskApiClient::class.java).getAllQuotes()
            response.body() ?: arrayListOf()
        }
    }
}