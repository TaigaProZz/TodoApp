package fr.todoapp.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
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
import fr.todoapp.databinding.TaskAdapterBinding
import kotlinx.android.synthetic.main.task_adapter.view.*

class TaskAdapter(val tasks: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    // add the task to th list
    fun addTodo(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)

    }

    fun removeItem(position: Int) {

        tasks.removeAt(position)
        notifyItemRemoved(tasks.size - 1)
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
            viewHolder.layoutPosition
            return@setOnLongClickListener true
        }


    }


    private fun showPopup(view: View) {
        this.let {
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.menuInflater.inflate(R.menu.long_press_on_task, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                return@setOnMenuItemClickListener true

            }
            popupMenu.show()
        }
    }




    override fun getItemCount(): Int {
        return tasks.size
    }

}

