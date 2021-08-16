package fr.todoapp.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.compose.material.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.todoapp.R
import fr.todoapp.Task
import kotlinx.android.synthetic.main.task_adapter.view.*

class TaskAdapter(val tasks: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    val db = Firebase.database.reference
    private lateinit var auth: FirebaseAuth

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    // add the task to th list
    fun addTodo(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)

    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

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
        //viewHolder.itemView.setOnClickListener {
        //    println("la position : $position")

        //}
        viewHolder.itemView.apply {
            taskAdapterText.text = currentTask.taskName
            checkBoxAdapter.isChecked = currentTask.isChecked

        }

        viewHolder.itemView.setOnLongClickListener {

            showPopup(it)
            return@setOnLongClickListener true
        }
    }

    @TODO
    private fun showPopup(view: View){
        this.let {
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.menuInflater.inflate(R.layout.long_press_task, popupMenu.menu)

        }

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}
