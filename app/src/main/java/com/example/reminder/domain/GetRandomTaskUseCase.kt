package com.example.reminder.domain

import com.example.reminder.data.TaskModel
import java.util.*


class GetRandomTaskUseCase  {

    operator fun invoke(): TaskModel?{
//        val tasks = TaskProvider.tasks
        val tasks : List<TaskModel> = listOf(TaskModel("test", "description", 20221201))

        if(!tasks.isNullOrEmpty()){
            val randomNumber = (tasks.indices).random()
            return tasks[randomNumber]
        }
        return null
    }
}