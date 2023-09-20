package com.example.logisthelperNegrobov.fragments.bottom_menu_fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class TaskViewModel: ViewModel() {
    val selectedTask: MutableLiveData<Task> by lazy {
        MutableLiveData<Task>()
    }
}