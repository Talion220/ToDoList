package com.bignerdranch.android.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.todolist.databinding.ActivityListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var taskDao: TaskDao
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDao = TaskListApplication.database.taskDao()

        adapter = TaskAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            val tasks = taskDao.getAllTasks()

            launch(Dispatchers.Main) {
                adapter.submitList(tasks)
            }
        }


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskToDelete = adapter.currentList[position]

                GlobalScope.launch(Dispatchers.IO) {
                    val taskIdToDelete = taskToDelete.id
                    taskDao.deleteTask(taskIdToDelete)

                    val updatedTaskList = taskDao.getAllTasks()

                    withContext(Dispatchers.Main) {
                        adapter.submitList(updatedTaskList)
                    }
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@ListActivity, AddActivity::class.java)

            startActivity(intent)
        }
    }
}