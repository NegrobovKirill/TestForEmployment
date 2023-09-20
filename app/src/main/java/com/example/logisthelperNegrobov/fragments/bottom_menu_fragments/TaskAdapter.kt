package com.example.logisthelperNegrobov.fragments.bottom_menu_fragments

import android.content.Context
import android.content.Intent
import android.media.RouteListingPreference.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.ItemTaskBinding
import java.io.Serializable

class TaskAdapter(contextM: Context): ListAdapter<Task, TaskAdapter.TaskHolder>(Comparator()) {


    var context = contextM

    class TaskHolder(item: View, contextv: Context):RecyclerView.ViewHolder(item){
        private lateinit var taskViewModel: TaskViewModel
        val binding = ItemTaskBinding.bind(item)
        val context = contextv
        fun bind(task: Task) = with(binding){
            tvTaskTitle.text =task.typeGroze
            if (task.currentTask){
                tvCurrentTask.visibility = View.VISIBLE
            }else{
                tvCurrentTask.visibility = View.INVISIBLE
            }

            tvTaskDate.text = task.taskData
            tvTaskTime.text = task.taskTime

            tvStartPoint.text = task.startPoint
            tvEndPoint.text = task.endPoint
            if (task.taskSuccsess){
                constraintAllDetails.visibility = View.GONE
                cardTaskSuccsess.visibility = View.VISIBLE
            }else {
                constraintAllDetails.visibility = View.VISIBLE
                cardTaskSuccsess.visibility = View.GONE
                tvTaskDetails.text = task.taskDetails
                tvOrdersParametrs.text = task.orderParametrs
                button.setOnClickListener {
                    val activity = context as AppCompatActivity
                    //taskViewModel.selectedTask.value = task
                    activity.supportFragmentManager.beginTransaction().replace(R.id.taskDetailsFragment,TaskDetailsFragment()).addToBackStack(null).commit()
                }
            }

        }
    }

    class Comparator: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TaskHolder(view, context)
    }


    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(getItem(position))
    }

}