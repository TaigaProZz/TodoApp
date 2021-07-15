package fr.todoapp.tasklist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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


            // set the custom layout
            myPopupBuilder.setView(view)

            // set the title on the popup
            myPopupBuilder.setTitle(R.string.title_popup)

            // add the add task button
            myPopupBuilder.setPositiveButton(
                R.string.add_task
            ) { dialog, which ->
                // TODO add to the recycler view

                // collect the text from the EditText
                val task = editText.text.toString()
                println(" the task is :$task")

                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
            }

            // show the popup
            myPopupBuilder.show()
        }
    }
    
}
