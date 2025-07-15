package com.example.kotlincomposelearning.datasource

import com.example.kotlincomposelearning.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow


class DataSource {

    private val db = FirebaseFirestore.getInstance()

    private val _allTasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    private val allTasks: StateFlow<MutableList<Task>> = _allTasks


    fun saveTask(task: String, description : String, priority : Int){
        val taskMap = hashMapOf(
            "task" to task,
            "description" to description,
            "priority" to priority
        )

        db.collection("tasks")
            .document(task).set(taskMap).addOnCompleteListener {
            }.addOnFailureListener{

            }
    }

    fun getTasks(): Flow<MutableList<Task>> {

        val listaTarefas: MutableList<Task> = mutableListOf()

        db.collection("tasks").get().addOnCompleteListener { querySnapshot ->
            if (querySnapshot.isSuccessful) {
                for (documento in querySnapshot.result) {
                    val tarefa = documento.toObject(Task::class.java)
                    listaTarefas.add(tarefa)
                }
                _allTasks.value = listaTarefas
            }
        }

        return allTasks
    }

    fun deleteTask(task: String) {
        db.collection("tasks").document(task).delete()
            .addOnCompleteListener {
                // Sucesso na exclusão
            }
            .addOnFailureListener { it: Exception ->
                // Falha ao excluir
            }
    }


}