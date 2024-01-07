package com.bignerdranch.android.todolist
import androidx.room.Entity
import androidx.room.PrimaryKey

// Task.kt
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val priority: Int
)