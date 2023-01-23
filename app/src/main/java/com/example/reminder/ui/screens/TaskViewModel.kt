package com.example.reminder.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.data.TaskModel
import com.example.reminder.domain.GetRandomTaskUseCase
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    val _taskList = MutableLiveData<MutableList<TaskModel>>()
    val taskList: LiveData<MutableList<TaskModel>> = _taskList
    var getRandomTask = GetRandomTaskUseCase()

    fun onCreate() {
        viewModelScope.launch(){
            val result = getRandomTask()
            if(result!=null){
                _taskList.postValue(mutableListOf(result))
            }
        }
    }
}