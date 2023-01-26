package com.example.reminder.domain

import android.content.Context
import android.util.Log
import com.example.reminder.data.TaskRepository
import com.example.reminder.data.database.entities.toDatabase
import com.example.reminder.domain.model.Task

class UpdateTaskUseCase(context: Context) {
    private val repository = TaskRepository.getInstance(context)

    suspend operator fun invoke(task:Task?) {
        if (task != null) {
        repository.insertTask(task.toDatabase())
        }
    }
}
