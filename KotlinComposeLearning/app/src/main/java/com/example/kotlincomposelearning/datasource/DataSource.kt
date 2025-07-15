package com.example.kotlincomposelearning.datasource

import com.example.kotlincomposelearning.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

// Classe responsável por acessar dados do Firestore (repositório de dados da aplicação)
class DataSource {

    // Instância do Firebase Firestore
    private val db = FirebaseFirestore.getInstance()

    // Flow privado que contém todas as tarefas armazenadas (pode ser atualizado internamente)
    private val _allTasks = MutableStateFlow<MutableList<Task>>(mutableListOf())

    // Flow público apenas leitura para ser observado por outras partes da UI (ex: ViewModel ou Compose)
    private val allTasks: StateFlow<MutableList<Task>> = _allTasks

    /**
     * Salva uma nova tarefa no Firestore.
     *
     * @param task        Título da tarefa (também usado como ID do documento)
     * @param description Descrição da tarefa
     * @param priority    Nível de prioridade (0 = baixa, 1 = média, 2 = alta)
     */
    fun saveTask(task: String, description : String, priority : Int) {
        val taskMap = hashMapOf(
            "task" to task,
            "description" to description,
            "priority" to priority
        )

        // Cria ou sobrescreve um documento com ID = task
        db.collection("tasks")
            .document(task)
            .set(taskMap) // salva os dados no documento
            .addOnCompleteListener {
                // Sucesso ao salvar (opcional: adicionar log ou feedback)
            }
            .addOnFailureListener {
                // Erro ao salvar (opcional: exibir Toast ou log)
            }
    }

    /**
     * Recupera todas as tarefas do Firestore e atualiza o StateFlow.
     *
     * @return Um Flow observável contendo uma lista mutável de tarefas.
     */
    fun getTasks(): Flow<MutableList<Task>> {

        val listaTarefas: MutableList<Task> = mutableListOf()

        // Busca todos os documentos da coleção "tasks"
        db.collection("tasks")
            .get()
            .addOnCompleteListener { querySnapshot ->
                if (querySnapshot.isSuccessful) {
                    // Itera sobre cada documento retornado
                    for (documento in querySnapshot.result) {
                        // Converte o documento Firestore para um objeto Task
                        val tarefa = documento.toObject(Task::class.java)
                        listaTarefas.add(tarefa) // adiciona à lista temporária
                    }
                    // Atualiza o Flow com a nova lista
                    _allTasks.value = listaTarefas
                }
            }

        // Retorna o StateFlow que pode ser observado na UI
        return allTasks
    }

    /**
     * Exclui uma tarefa do Firestore com base no seu título (que é o ID do documento).
     *
     * @param task O título da tarefa (ID do documento a ser deletado)
     */
    fun deleteTask(task: String) {
        db.collection("tasks")
            .document(task)
            .delete()
            .addOnCompleteListener {
                // Sucesso na exclusão (opcional: logar ou atualizar UI)
            }
            .addOnFailureListener { it: Exception ->
                // Falha ao excluir (opcional: exibir Toast ou log de erro)
            }
    }
}
