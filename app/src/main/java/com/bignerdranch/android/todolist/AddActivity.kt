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
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var taskDao: TaskDao
    private lateinit var taskAdapter: TaskAdapter // Объявляем адаптер
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDao = TaskListApplication.database.taskDao()
        taskAdapter = TaskAdapter()

        val buttonAdd = binding.buttonAdd

        buttonAdd.setOnClickListener {

            val description = binding.editTextDescription.text.toString()
            val priority = getSelectedPriority()

            GlobalScope.launch(Dispatchers.IO) {
                taskDao.insertTask(Task(description = description, priority = priority))
                // Получаем новый список задач после вставки
                val updatedTaskList = taskDao.getAllTasks()

                // Передаем новый список в адаптер и уведомляем RecyclerView
                withContext(Dispatchers.Main) {
                    taskAdapter.submitList(updatedTaskList)
                    taskAdapter.notifyDataSetChanged()
                }
            }

            finish()
        }
    }

    private fun getSelectedPriority(): Int {
        val priorityRadioGroup: RadioGroup = findViewById(R.id.radioGroupPriority)
        val selectedRadioButton: RadioButton? = findViewById(priorityRadioGroup.checkedRadioButtonId)

        return when (selectedRadioButton?.text.toString().toLowerCase()) {
            "high" -> 1
            "medium" -> 2
            "low" -> 3
            else -> 0
        }
    }
}
