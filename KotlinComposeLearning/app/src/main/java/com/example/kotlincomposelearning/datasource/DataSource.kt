package com.example.kotlincomposelearning.datasource

import com.google.firebase.firestore.FirebaseFirestore

class DataSource {

    private val db = FirebaseFirestore.getInstance()

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
}