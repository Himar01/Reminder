package com.example.reminder.domain

import com.example.reminder.data.TaskModel

class GetTasksListUseCase {

    operator fun invoke(): List<TaskModel>?{
//        val tasks = TaskProvider.tasks
        val tasks : List<TaskModel> = listOf(TaskModel("test", "description", 20221201))

        if(!tasks.isNullOrEmpty()){
            return tasks
        }
        return null
    }
}