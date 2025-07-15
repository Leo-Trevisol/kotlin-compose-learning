package com.example.kotlincomposelearning.repository

import com.example.kotlincomposelearning.datasource.DataSource

class TasksRepository() {

    private val dataSource = DataSource()

    fun saveTask(task: String, description: String, priority: Int) {

        dataSource.saveTask(
                task = task,
                description = description,
                priority = priority
            )
        }

    fun getTasks() = dataSource.getTasks()

    fun deleteTask(task: String) {
        dataSource.deleteTask(task)
    }

}