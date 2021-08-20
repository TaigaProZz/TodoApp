package fr.todoapp.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.todoapp.R
import fr.todoapp.Task
import kotlinx.android.synthetic.main.task_adapter.view.*

class TaskAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    // add the task to th list
    fun addTodo(task: Task) {
        tasks.add(task)
        notifyItemChanged(tasks.size - 1)

    }

    private fun removeItem(position: Int) {
        tasks.removeAt(position)
        notifyItemRangeRemoved(position , tasks.size + 1)


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

            viewHolder.itemView.tag = position


            showPopup(it)

            return@setOnLongClickListener true
        }


    }

    private fun showPopup(view: View) {
        this.let {
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.menuInflater.inflate(R.menu.long_press_on_task, popupMenu.menu)

            popupMenu.menu.add(Menu.NONE, 0, 0,"Marquer comme accomplie")
            popupMenu.menu.add(Menu.NONE, 1, 1,"Delete")

            popupMenu.setOnMenuItemClickListener {


                val id = it.itemId

                if (id == 0){
                    val getPositionTag = view.tag


                    Toast.makeText(view.context, "$getPositionTag", Toast.LENGTH_LONG).show()

                }
                else if(id == 1){
                    val getPositionTag = view.tag

                    removeItem(getPositionTag as Int)

                }

                return@setOnMenuItemClickListener true

            }

            popupMenu.show()
        }

    }



    override fun getItemCount(): Int {
        return tasks.size
    }

}

