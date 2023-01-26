package com.example.reminder.ui.screens.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.domain.GetRandomTaskUseCase
import com.example.reminder.domain.GetTasksListUseCase
import com.example.reminder.domain.UpdateTaskUseCase
import com.example.reminder.domain.model.Task
import kotlinx.coroutines.*


class TaskViewModel(context: Context) : ViewModel() {

    private val _taskList = MutableLiveData<MutableList<Task>>()
    val taskList: LiveData<MutableList<Task>> = _taskList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mindTextStyle = MutableLiveData<TextStyle>()
    val mindTextStyle: LiveData<TextStyle> = _mindTextStyle

    private val _taskTextStyle = MutableLiveData<TextStyle>()
    val taskTextStyle: LiveData<TextStyle> = _taskTextStyle

    private val _backgroundOn = MutableLiveData<Boolean>()
    val backgroundOn: LiveData<Boolean> = _backgroundOn

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task> = _selectedTask

    private val _dialogShown = MutableLiveData<Boolean?>()
    val dialogShown: LiveData<Boolean?> = _dialogShown

    val getRandomTask = GetRandomTaskUseCase(context)
    val loadTaskList = GetTasksListUseCase(context)
    val updateDatabaseTask = UpdateTaskUseCase(context)

    private val mainTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )

    private val backgroundTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 20.sp
    )

    fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.postValue(oldValue)
    }
    //    try{
//        val result = loadTaskList()
//        _taskList.postValue(result)
//    }catch( e: Exception){
//        Log.e("ViewModel.onCreate()", "Error: $e")
//        delay(4000)
//        onCreate()
//    }
    fun onCreate() {
        viewModelScope.launch() {
            val result = loadTaskList()
            _taskList.postValue(result)
        }
    }

    fun onScreenChanged(isMindScreen: Boolean) {
        _backgroundOn.value = isMindScreen
        if (isMindScreen) {
            _mindTextStyle.value = mainTextStyle
            _taskTextStyle.value = backgroundTextStyle
        } else {
            _mindTextStyle.value = backgroundTextStyle
            _taskTextStyle.value = mainTextStyle
        }
    }

    fun showDialog(bool: Boolean){
        _dialogShown.value = bool
    }


    fun changeSelectedTask(task: Task?) {
        _selectedTask.value = task
        Log.e("ViewModel", "Selected Task Changed")
    }

    fun updateTask() {
        viewModelScope.launch() {
            updateDatabaseTask(_selectedTask.value)
            val result = loadTaskList()
            _taskList.postValue(result)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch() {
            updateDatabaseTask(task)
            val result = loadTaskList()
            _taskList.postValue(result)
        }
    }
    fun deleteTask(task: Task?) {

    }

}