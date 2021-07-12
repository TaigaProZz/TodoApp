package fr.todoapp.tasklist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.todoapp.R

class TaskListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
    }
}