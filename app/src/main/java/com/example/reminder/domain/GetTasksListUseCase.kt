package com.example.reminder.domain

import android.content.Context
import com.example.reminder.data.TaskRepository
import com.example.reminder.data.database.entities.toDatabase
import com.example.reminder.domain.model.Task

class GetTasksListUseCase(context: Context) {

        private val repository = TaskRepository.getInstance(context)

        suspend operator fun invoke():MutableList<Task> {
                return repository.getAllTasksFromDatabase()
        }
}