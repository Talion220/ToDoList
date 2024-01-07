package com.bignerdranch.android.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.todolist.databinding.ActivityAddBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var taskDao: TaskDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add)

        taskDao = TaskListApplication.database.taskDao()
        val buttonAdd = binding.buttonAdd

        buttonAdd.setOnClickListener {

            val description = binding.editTextDescription.text.toString()
            val priority = getSelectedPriority()

            GlobalScope.launch(Dispatchers.IO) {
                taskDao.insertTask(Task(description = description, priority = priority))
            }

            finish()
        }

    }
    private fun getSelectedPriority(): Int {
        val priorityRadioGroup: RadioGroup = findViewById(R.id.radioGroupPriority)

        val selectedRadioButton: RadioButton? = findViewById(priorityRadioGroup.checkedRadioButtonId)

        return when (selectedRadioButton?.text.toString().toLowerCase()) {
            "high" -> 1 // Пример: высокий приоритет
            "medium" -> 2 // Пример: средний приоритет
            "low" -> 3 // Пример: низкий приоритет
            else -> 0 // По умолчанию, если ничего не выбрано
        }
    }
}