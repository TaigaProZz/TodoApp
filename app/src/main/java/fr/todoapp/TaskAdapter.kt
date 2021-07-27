package fr.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.task_adapter.view.*

class TaskAdapter(val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    val db = Firebase.database.reference
    private lateinit var auth: FirebaseAuth

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // add the task to th list
    fun addTodo(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_adapter,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentTask = tasks[position]
        val auth = Firebase.auth

        viewHolder.itemView.apply {
            taskAdapterText.text = currentTask.taskName
            checkBoxAdapter.isChecked = currentTask.isChecked
            val user = auth.currentUser
            val userId = user?.uid


        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}
