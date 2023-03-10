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
import com.example.reminder.domain.DeleteTaskUseCase
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

    private val _isMindOn = MutableLiveData<Boolean>()
    val isMindOn: LiveData<Boolean> = _isMindOn

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task> = _selectedTask

    private val _dialogShown = MutableLiveData<Boolean?>()
    val dialogShown: LiveData<Boolean?> = _dialogShown

    private val _completedTaskVisible = MutableLiveData<Boolean>()
    val completedTaskVisible: LiveData<Boolean> = _completedTaskVisible

    private val _update = MutableLiveData<Boolean>()
    val update: LiveData<Boolean> = _update


    private val getRandomTaskFromApi = GetRandomTaskUseCase(context)
    private val loadTaskList = GetTasksListUseCase(context)
    private val updateDatabaseTask = UpdateTaskUseCase(context)
    private val deleteTaskFromDatabase = DeleteTaskUseCase(context)

    private val mainTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )

    private val backgroundTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 20.sp
    )

//    fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
//        val oldValue = this.value ?: mutableListOf()
//        oldValue.add(item)
//        this.postValue(oldValue)
//    }

    fun onCreate() {
        viewModelScope.launch() {
            _isLoading.value = true
            val result = loadTaskList()
            _taskList.postValue(result)
            _isLoading.value = false
        }
    }

    fun setRandomTask(setRandomTask: (Task?) -> Unit ){
        if(!_isLoading.value!!) {
            viewModelScope.launch () {
                _isLoading.value = true
                var count = 0
                while(_isLoading.value!!){
                    count++
                    try {
                        _isLoading.value = true
                        val randomTask = getRandomTaskFromApi()
                        setRandomTask(randomTask)
                        _isLoading.value = false
                    } catch (e: java.lang.Exception) {
                        Log.e("Getting Random Task from API", e.toString())
                        delay(1000)
                    }
                    if(count>=5) {
                        _isLoading.value = false
                        setRandomTask(null)
                    }
                }
            }
        }
    }

    fun onScreenChanged(isMindScreen: Boolean) {
        _isMindOn.value = isMindScreen
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
            Log.e("updatedTask", _selectedTask.value.toString())
            val result = loadTaskList()
            _taskList.postValue(result)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch() {
            updateDatabaseTask(task)
            val result = loadTaskList()
            _taskList.postValue(result)
            updateScreen()
        }
    }

    fun changeCompletedTaskVisibility(){
        _completedTaskVisible.value = if(completedTaskVisible.value!=null) !(completedTaskVisible.value!!) else true
    }

    fun updateScreen(){
        _update.value = if(update.value!=null) !(update.value!!) else true
    }
    fun deleteTask(task: Task?) {
        viewModelScope.launch() {
            deleteTaskFromDatabase(task)
            val result = loadTaskList()
            _taskList.postValue(result)
            updateScreen()
        }
    }

}