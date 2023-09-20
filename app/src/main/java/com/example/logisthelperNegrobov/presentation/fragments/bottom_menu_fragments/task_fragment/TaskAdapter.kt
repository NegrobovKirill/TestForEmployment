package com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.task_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.ItemTaskBinding
import com.example.logisthelperNegrobov.domain.models.Task

class TaskAdapter(contextM: Context): ListAdapter<Task, TaskAdapter.TaskHolder>(Comparator()) {

    private lateinit var mListener : RecyclerViewInterface
    var context = contextM

    fun setOnItemClickListener(listener: RecyclerViewInterface){
        mListener = listener
    }

    class TaskHolder(item: View, listener: RecyclerViewInterface):RecyclerView.ViewHolder(item){
        private lateinit var taskViewModel: TaskViewModel
        val binding = ItemTaskBinding.bind(item)

        init {
            binding.button.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

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
        return TaskHolder(view,mListener)
    }


    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(getItem(position))
    }

}