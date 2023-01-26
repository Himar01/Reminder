package com.example.reminder.domain

import android.content.Context
import com.example.reminder.data.TaskRepository
import com.example.reminder.data.model.RandomTaskProvider
import com.example.reminder.data.model.TaskModel
import com.example.reminder.data.model.toModel
import com.example.reminder.data.network.TaskApiClient
import com.example.reminder.domain.model.Task
import com.example.reminder.domain.model.toDomain


class GetRandomTaskUseCase(context: Context)  {


    private val repository = TaskRepository.getInstance(context)

    suspend operator fun invoke(): Task?{
        val tasks : MutableList<Task> = RandomTaskProvider.tasks.map { it.toDomain() }.toMutableList()
        if(!tasks.isNullOrEmpty()){
            val randomNumber = (tasks.indices).random()
            return tasks[randomNumber]
        }
        val apiTasks : MutableList<Task>  = repository.getRandomTasks()
        if(!apiTasks.isNullOrEmpty()){
            RandomTaskProvider.tasks = apiTasks.map { it.toModel() }.toMutableList()
            val randomNumber = (tasks.indices).random()
            return tasks[randomNumber]
        }
        return null
    }
}