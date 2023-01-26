package com.example.reminder.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminder.domain.model.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true )
    @ColumnInfo(name = "id")var id: Int = 0,
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "description")var description: String?,
    @ColumnInfo(name = "date")var date: Int?,
    @ColumnInfo(name = "completed")var completed: Boolean,
    @ColumnInfo(name = "isMind")var isMind: Boolean = false
)

fun Task.toDatabase():TaskEntity  {
    return if(id == 0){
        TaskEntity(name = name, description = description, date = date, completed = completed)
    } else {
        TaskEntity(id = id, name = name, description = description, date = date, completed = completed)
    }

}