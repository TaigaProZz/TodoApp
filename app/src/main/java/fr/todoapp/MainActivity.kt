package fr.todoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.todoapp.tasklist.TaskListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gotoTaskButton = findViewById<Button>(R.id.gotoTaskButton).setOnClickListener{
            startActivity(Intent (applicationContext, TaskListActivity::class.java))
        }
    }
}