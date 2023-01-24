package com.example.reminder.ui.screens

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
import com.example.reminder.data.model.TaskModel
import com.example.reminder.domain.GetRandomTaskUseCase
import com.example.reminder.domain.GetTasksListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TaskViewModel(context: Context) : ViewModel() {

    private val _taskList = MutableLiveData<MutableList<TaskModel>>()
    val taskList: LiveData<MutableList<TaskModel>> = _taskList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mindTextStyle = MutableLiveData<TextStyle>()
    val mindTextStyle: LiveData<TextStyle> = _mindTextStyle

    private val _taskTextStyle = MutableLiveData<TextStyle>()
    val taskTextStyle: LiveData<TextStyle> = _taskTextStyle

    private val _backgroundOn = MutableLiveData<Boolean>()
    val backgroundOn: LiveData<Boolean> = _backgroundOn

    var getRandomTask = GetRandomTaskUseCase()
    var loadTaskList = GetTasksListUseCase(context)

    private val mainTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )
    private val backgroundTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 20.sp
    )

    fun onCreate() {
        viewModelScope.launch(){
            try{
                val result = loadTaskList()
                _taskList.postValue(result)
            }catch( e: Exception){
                Log.e("ViewModel.onCreate()", "Error: $e")
                delay(4000)
                onCreate()
            }

        }
    }

    fun onScreenChanged(isMindScreen: Boolean){
        _backgroundOn.value = isMindScreen
        if(isMindScreen){
            _mindTextStyle.value = mainTextStyle
            _taskTextStyle.value = backgroundTextStyle
        } else{
            _mindTextStyle.value = backgroundTextStyle
            _taskTextStyle.value = mainTextStyle
        }

    }

    fun taskCompleted(){
        _taskList.apply {  }
    }
}