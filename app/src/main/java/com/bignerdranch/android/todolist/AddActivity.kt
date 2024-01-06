package com.bignerdranch.android.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.todolist.databinding.ActivityAddBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Доступ к компонентам макета через binding
        val editTextDescription = binding.editTextDescription
        val radioGroupPriority = binding.radioGroupPriority
        val buttonAdd = binding.buttonAdd

        // Ваш код для работы с editTextDescription, radioGroupPriority и buttonAdd


        buttonAdd.setOnClickListener {

            val intent = Intent(this@AddActivity, ListActivity::class.java)

            // Запускаем AddActivity
            startActivity(intent)
        }
    }
}