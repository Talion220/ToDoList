package com.bignerdranch.android.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.bignerdranch.android.todolist.databinding.ActivityListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Создаем Intent для перехода к AddActivity
            val intent = Intent(this@ListActivity, AddActivity::class.java)

            // Запускаем AddActivity
            startActivity(intent)
        }
    }

}