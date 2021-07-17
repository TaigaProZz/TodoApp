package fr.todoapp.tasklist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.todoapp.R
import fr.todoapp.Task
import fr.todoapp.TaskAdapter
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val db = Firebase.database.reference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)


        taskAdapter = TaskAdapter(mutableListOf())

        // recycler view parameters
        recycler_view_task.adapter = taskAdapter
        recycler_view_task.layoutManager =
            LinearLayoutManager(this)
        


        // add button on the task activity list view
        val addTaskButton = findViewById<Button>(R.id.add_task_button).setOnClickListener {

            // call custom popup function when @addTaskButton is clicked
            onCreateDialog(savedInstanceState)


        }
    }

    // function to create a custom popup
    private fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val activity = TaskListActivity::class.java

        return activity.let {

            val myPopupBuilder = AlertDialog.Builder(this@TaskListActivity)
            val inflater = this.layoutInflater
            val view = inflater.inflate(R.layout.activity_custom_popup, null, false)
            val editText = view.findViewById<EditText>(R.id.editText_Popup)
            val auth = Firebase.auth


            // set the custom layout
            myPopupBuilder.setView(view)

            // set the title on the popup
            myPopupBuilder.setTitle(R.string.title_popup)

            // add the add task button
            myPopupBuilder.setPositiveButton(
                R.string.add_task
            ) { dialog, which ->

                // collect the text from the EditText
                val taskName = editText.text.toString()
                if (taskName.isNotEmpty()) {

                    // create and add the task to the recycler view

                    val task = Task(taskName)
                    taskAdapter.addTodo(task)
                    val user = auth.currentUser
                    val userId = user?.uid
                    if (userId != null) {
                        db.child("users").child(userId).child("tasks")
                    }
                    println(" the task is $task")
                    editText.text.clear()


                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                }

            }

            // show the popup
            myPopupBuilder.show()
        }
    }
}
