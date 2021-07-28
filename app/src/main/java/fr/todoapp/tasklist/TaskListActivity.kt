package fr.todoapp.tasklist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import fr.todoapp.*
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.task_adapter.*

class TaskListActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarTaskActivity)
        setSupportActionBar(toolbar)

        // initialize firebase val
        val auth = Firebase.auth
        val user = auth.currentUser
        val userId = user?.uid


        taskAdapter = TaskAdapter(mutableListOf())

        // collect task data from firebase
        db.collection("users").document("$userId")
            .collection("tasks")
            .get()
            .addOnSuccessListener {
                for (document in it) {

                    // convert the firebase data to the Task object
                    val setDocToTask = document.toObject<Task>()

                    // collect doc id who equals to the task name
                    val name = document.id


                    // initialize the task object with the task name collected
                    val task = Task(name)

                    // add the collected task to the list and recycler view
                    taskAdapter.addTodo(task)
                }
            }
            .addOnFailureListener {
                Log.d("Fail to get", "Fail")
            }


        // recycler view parameters
        recycler_view_task.adapter = taskAdapter
        recycler_view_task.layoutManager =
            LinearLayoutManager(this)


        // add button on the task activity list view
        add_task_button.setOnClickListener {
            // call custom popup function when @addTaskButton is clicked
            onCreateDialog(savedInstanceState)

        }
    }


    // function to create a custom popup
    private fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val activity = TaskListActivity::class.java

        return activity.let {

            // initialize variables of popup instance
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
            )

            { dialog, which ->

                // collect the text from the EditText
                val taskName = editText.text.toString()

                // check if the edit text isnt empty
                if (taskName.isNotEmpty()) {

                    // create and add the task to the recycler view
                    val task = Task(taskName)
                    val user = auth.currentUser
                    val userId = user?.uid

                    // add the task to the recycler view
                    taskAdapter.addTodo(task)

                    // task data
                    val taskData = hashMapOf(
                        "task" to task,
                    )

                    // add the task to firebase
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .collection("tasks").document(taskName)
                            .set(taskData)
                    }

                    // clear edit text and toast
                    editText.text.clear()
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                }
            }
            // show the popup
            myPopupBuilder.show()
        }
    }
}
