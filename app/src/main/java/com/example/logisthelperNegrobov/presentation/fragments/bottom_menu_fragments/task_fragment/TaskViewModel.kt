package com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.task_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logisthelperNegrobov.domain.models.Task

open class TaskViewModel: ViewModel() {
    val selectedTask: MutableLiveData<Task> by lazy {
        MutableLiveData<Task>()
    }
}