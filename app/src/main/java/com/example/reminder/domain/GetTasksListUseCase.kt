package com.example.reminder.domain

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.reminder.data.TaskRepository
import com.example.reminder.data.model.TaskModel
import java.lang.ref.WeakReference

class GetTasksListUseCase(context: Context) {

        private val repository = TaskRepository.getInstance(context)

        suspend operator fun invoke():MutableList<TaskModel> {
                return repository.getAllTasks()
        }

}