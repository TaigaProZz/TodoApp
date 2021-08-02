package fr.todoapp.taskList

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.todoapp.*
import fr.todoapp.recyclerViewAdapters.TaskAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.activity_task_list.goBackBtn
import kotlinx.android.synthetic.main.task_adapter.*
import kotlinx.android.synthetic.main.task_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class TaskListActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter

    // initialize firebase val
    private val db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser
    private val userId = user?.uid
    private var myList = ArrayList<Task>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        // call functions
        // to load shared preferences for tasks
        loadDataSharedPreferences()

        // to create the recycler view
        createRecyclerView()


        // toolbar parameter
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarTaskActivity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // ****************** SET ON CLICK LISTENERS ****************** \\

        // go back
        goBackBtn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        // add button on the task activity list view
        add_task_button.setOnClickListener {
            // call custom popup function when @addTaskButton is clicked
            onCreateDialog(savedInstanceState)
        }

        // clear the list button
        clearTaskList.setOnClickListener {
            deleteSharedPreferences("data")
            finish()
            startActivity(Intent(applicationContext, TaskListActivity::class.java))
        }

        // collect data from firebase
        buttonGetDataFb.setOnClickListener {
            getDataFromFirebase()
            saveDataSharedPreferences()

        }

    }

    override fun onDestroy() {
        super.onDestroy()

        saveDataSharedPreferences()
    }


    // function to create a custom popup
    @SuppressLint("ResourceType")
    private fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val activity = TaskListActivity::class.java

        return activity.let {

            // initialize variables of popup instance
            val myPopupBuilder = AlertDialog.Builder(this@TaskListActivity)
            val inflater = this.layoutInflater
            val view = inflater.inflate(R.xml.custom_popup, null, false)
            val editText = view.findViewById<EditText>(R.id.editText_Popup)

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

    // fun to save locally data of tasks with shared preferences
    private fun saveDataSharedPreferences() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(myList)

        if (!sharedPreferences.getString("taskList", "")?.equals(json)!!) {
            editor.putString("taskList", json)
            editor.apply()
        }
    }

    // fun to load the data of shared preferences saved by @saveDataSharedPreferences function
    private fun loadDataSharedPreferences() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("taskList", myList.toString())
        val type = object : TypeToken<ArrayList<Task>>() {}.type

        myList = gson.fromJson(json, type)

    }

    // create my recycler view
    private fun createRecyclerView() {

        val mRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_task)
        val mLayoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(myList)

        // recycler view parameters
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = taskAdapter

    }

    //collect task data from firebase
    private fun getDataFromFirebase() {

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
                    
                    // check if the task already exist
                    val nameExist = task in myList
                    if (nameExist){
                        break
                    }
                    else{
                        // add the collected task to the list and recycler view
                        taskAdapter.addTodo(task)
                    }
                }
            }
            .addOnFailureListener {
                Log.d("Fail to get", "Fail")
            }
    }

}
