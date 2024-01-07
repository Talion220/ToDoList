package com.bignerdranch.android.todolist
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// TaskDao.kt
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    suspend fun getAllTasks(): List<Task>

    @Insert
    suspend fun insertTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)
}