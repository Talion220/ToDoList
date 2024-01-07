package com.bignerdranch.android.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.todolist.databinding.ActivityListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            // Получаем список задач из базы данных
            val tasks = taskDao.getAllTasks()

            // Обновляем пользовательский интерфейс в основном потоке
            launch(Dispatchers.Main) {
                // Обновляем RecyclerView с полученным списком задач
                adapter.submitList(tasks)
            }
        }

//        // Добавим обработчик смахивания элемента в RecyclerView для удаления
//        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                // Получаем позицию смахиваемого элемента
//                val position = viewHolder.adapterPosition
//                // Получаем задачу, которую хотим удалить
//                val taskToDelete = adapter.currentList[position]
//
//                // Запускаем корутину для выполнения операции в фоновом потоке
//                GlobalScope.launch(Dispatchers.IO) {
//                    // Удаляем задачу из базы данных
//                    taskDao.deleteTask(taskToDelete)
//                }
//            }
//        })
//
//        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Создаем Intent для перехода к AddActivity
            val intent = Intent(this@ListActivity, AddActivity::class.java)

            // Запускаем AddActivity
            startActivity(intent)
        }
    }
}