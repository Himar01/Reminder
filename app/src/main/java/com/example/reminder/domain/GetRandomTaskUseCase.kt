package com.example.reminder.domain

import com.example.reminder.data.model.TaskModel
import com.example.reminder.data.model.TaskProvider


class GetRandomTaskUseCase()  {

    operator fun invoke(): TaskModel?{

        val tasks : List<TaskModel> = TaskProvider.tasks
        if(!tasks.isNullOrEmpty()){
            val randomNumber = (tasks.indices).random()
            return tasks[randomNumber]
        }
        return null
    }
}