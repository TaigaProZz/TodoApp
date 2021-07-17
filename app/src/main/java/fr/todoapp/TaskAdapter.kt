package fr.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.task_adapter.view.*

class TaskAdapter(
    val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        viewHolder.itemView.apply {
            taskAdapterText.text = currentTask.taskName
            checkBoxAdapter.isChecked = currentTask.isChecked

        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


}