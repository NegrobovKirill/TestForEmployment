package com.example.logisthelperNegrobov.domain.models

data class Task(
    val taskId: Int,
    val typeGroze: String,
    val currentTask: Boolean,
    val city: String,
    val taskData: String,
    val taskTime: String,
    val startPoint: String,
    val endPoint: String,
    val typeTrack: String?,
    val taskSuccsess: Boolean,
    val taskDetails: String?,
    val orderParametrs: String?,
    val phoneOrder: String?,
    val FIO: String?
)
