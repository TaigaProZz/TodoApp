package fr.todoapp.tasklist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import fr.todoapp.R

class TaskListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        // add button on the task activity list view
        val addTaskButton = findViewById<Button>(R.id.add_task_button).setOnClickListener {
            val myPopup = android.app.AlertDialog.Builder(this)
            val input = EditText(this)


            myPopup.setView(input)

            myPopup.setTitle("Quelle tâche veux-tu ajouter?")

            // add task button ( in the pop up )
            myPopup.setPositiveButton("Ajouter"){ dialog, which ->
                Toast.makeText(applicationContext, "Tâche bien ajoutée", Toast.LENGTH_SHORT).show()
            }


            myPopup.show()

        }
    }
}
