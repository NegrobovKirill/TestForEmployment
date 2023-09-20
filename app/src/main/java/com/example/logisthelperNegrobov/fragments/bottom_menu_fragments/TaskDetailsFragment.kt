package com.example.logisthelperNegrobov.fragments.bottom_menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentTaskDetailsBinding
import com.example.logisthelperNegrobov.fragments.AuthFragmentViewModel


class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding

    private val taskViewModel: TaskViewModel by activityViewModels()

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TaskDetailsFragment()
    }
}