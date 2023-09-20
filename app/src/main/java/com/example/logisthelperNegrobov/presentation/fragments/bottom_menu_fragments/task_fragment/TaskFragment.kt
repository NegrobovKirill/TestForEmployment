package com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.task_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentTaskBinding
import com.example.logisthelperNegrobov.domain.models.Task

class TaskFragment : Fragment() , RecyclerViewInterface {

    private lateinit var navController: NavController

    private val taskViewModel: TaskViewModel by activityViewModels()

    private lateinit var binding: FragmentTaskBinding
    private lateinit var adapter: TaskAdapter

    private val task1 = Task(
        1,"Мебель1", true, "Москва","11.08.2023","11:00",
        "Склад 51, Ул. Пушкина 124Б", "Склад 38, Ул. Розенбаума 89","Тентованный",
        false, "Прописанные детали заказа", "Прописанные параметры по оплате",
        "+71112223344","Иванов Владимир Иосифович"
    )

    private val task2 = Task(
        2,"Мебель2", false, "Москва","01.08.2023","12:00",
        "Склад 1, Ул. Комсомольская 384", "Склад 11, ул. Радищева 145В","Тентованный",
        false, "Прописанные детали заказа", "Прописанные параметры по оплате",
        "+79991115566","Иванов Иван Иосифович"
    )

    private val task3 = Task(
        3,"Мебель3", false, "Воронеж","21.08.2023","13:00",
        "Склад 1, Ул. Комсомольская 384", "Склад 11, ул. Радищева 145В","Тентованный",
        true, null, null,"+76665554433","Иванов Иосиф Владимирович"
    )



    private val tasksList = listOf<Task>(task1,task2,task3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TaskAdapter(requireContext())
        binding.rcViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewTasks.adapter = adapter
        adapter.setOnItemClickListener(object : RecyclerViewInterface {
            //Implement

            override fun onClick(position: Int) {
                taskViewModel.selectedTask.value = tasksList[position]

                parentFragmentManager.beginTransaction().replace(R.id.mainFragmentHolder,
                    TaskDetailsFragment()
                ).commit()


            }

        })
        adapter.submitList(tasksList)

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TaskFragment()
    }

    override fun onClick(position: Int) {

    }

}