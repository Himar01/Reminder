package com.example.reminder.data.database.dao

import androidx.room.*
import com.example.reminder.data.database.entities.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    suspend fun getAllTasks():List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tasks:TaskEntity)

    @Delete
    suspend fun delete(task:TaskEntity)
}