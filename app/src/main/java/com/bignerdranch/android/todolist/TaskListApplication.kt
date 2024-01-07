package com.bignerdranch.android.todolist

import android.app.Application
import androidx.room.Room

class TaskListApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Инициализация базы данных
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "task_database")
            .build()
    }
}