package com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.task_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentTaskDetailsBinding
import com.example.logisthelperNegrobov.domain.models.Task
import com.example.logisthelperNegrobov.presentation.fragments.MainFragment


class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding

    private val taskViewModel: TaskViewModel by activityViewModels()

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //callback to parent activity
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction().replace(R.id.mainFragmentHolder,
                    MainFragment()
                ).commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = taskViewModel.selectedTask.value!!
        binding.apply {
            tvGroze.text = task.typeGroze
            tvCity.text = task.city
            tvDate.text = task.taskData
            tvTime.text = task.taskTime
            tvStartPointDerection.text = task.startPoint
            tvEndPointDirection.text = task.endPoint
            tvTrackType.text = task.typeTrack
            tvOrderDetails.text = task.taskDetails
            tvOrderParametrs.text = task.orderParametrs
            tvPhoneNumber.text = task.phoneOrder
            tvFIO.text = task.FIO
            if (task.currentTask){
                constraintIncident.visibility = View.VISIBLE
                constraintAcceptRefuse.visibility = View.GONE
            }else
                constraintIncident.visibility = View.GONE
                constraintAcceptRefuse.visibility = View.VISIBLE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TaskDetailsFragment()
    }


}