package com.example.reminder.data.network

import com.example.reminder.data.model.TaskModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/tasks.json")
    suspend fun getAllQuotes(): Response<MutableList<TaskModel>>
}