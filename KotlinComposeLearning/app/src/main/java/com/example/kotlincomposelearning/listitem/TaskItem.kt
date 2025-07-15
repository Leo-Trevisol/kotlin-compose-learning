package com.example.kotlincomposelearning.listitem

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.kotlincomposelearning.R
import com.example.kotlincomposelearning.model.Task
import com.example.kotlincomposelearning.repository.TasksRepository
import com.example.kotlincomposelearning.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    position: Int = 0, // posição da tarefa na lista
    taskList: MutableList<Task>, // lista completa de tarefas
    context: Context, // contexto Android (necessário para Toast)
    navController: NavController // para navegar entre telas
) {
    // Recupera os dados da tarefa na posição atual, com valores padrão se nulos
    val taskTitle = taskList[position].task ?: "Task ${position + 1}"
    val taskDescription = taskList[position].description ?: "Description"
    val taskPriority = taskList[position].priority ?: 3

    // Define cor com base na prioridade da tarefa
    val colorPriority = when (taskPriority) {
        0 -> RADIO_BUTTON_GREEN_ENABLED
        1 -> RADIO_BUTTON_YELLOW_ENABLED
        2 -> RADIO_BUTTON_RED_ENABLED
        else -> Black
    }

    // Converte o valor numérico da prioridade para texto legível
    val priorityLevel = when (taskPriority) {
        0 -> "Low priority"
        1 -> "Medium priority"
        2 -> "High priority"
        else -> "High priority"
    }

    // Estado para controlar a visibilidade do AlertDialog
    val openDialog = remember { mutableStateOf(false) }

    // Cria escopo para executar coroutines dentro de um Composable
    val scope = rememberCoroutineScope()

    // Repositório responsável por manipular tarefas (Firebase ou local)
    val tasksRepository = TasksRepository()

    // Se o usuário clicar no botão deletar, esse diálogo será mostrado
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false }, // fecha o diálogo se clicar fora
            title = { Text("Deletar Tarefa") }, // título do diálogo
            text = { Text("Deseja excluir a tarefa?") }, // mensagem do diálogo
            confirmButton = {
                // Botão "Sim" que deleta a tarefa
                TextButton(onClick = {
                    // Chama a função do repositório que deleta a tarefa (ex: por ID)
                    tasksRepository.deleteTask(taskTitle)

                    // Lança uma coroutine para atualizar a UI após deletar
                    scope.launch {
                        taskList.removeAt(position) // remove localmente da lista
                        navController.navigate("listTasks") { // volta pra lista de tarefas
                            popUpTo("listTasks") { inclusive = true } // limpa a pilha
                        }
                        Toast.makeText(context, "Sucesso ao deletar tarefa", Toast.LENGTH_SHORT).show()
                    }

                    // Fecha o diálogo
                    openDialog.value = false
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                // Botão "Não", apenas fecha o diálogo
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Text("Não")
                }
            }
        )
    }

    // Card principal que representa visualmente a tarefa
    Card(
        colors = CardDefaults.cardColors(containerColor = White), // fundo branco
        modifier = Modifier
            .fillMaxWidth() // ocupa toda a largura do container
            .padding(10.dp) // espaço externo
    ) {
        // Layout de restrições (semelhante ao ConstraintLayout do XML)
        ConstraintLayout(modifier = Modifier.padding(20.dp)) {
            // Cria referências para os elementos que serão posicionados
            val (txtTitle, txtDescription, priorityCard, txtPriority, btDelete) = createRefs()

            // Texto com o título da tarefa
            Text(
                text = taskTitle,
                modifier = Modifier.constrainAs(txtTitle) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            // Texto com a descrição da tarefa
            Text(
                text = taskDescription,
                modifier = Modifier.constrainAs(txtDescription) {
                    top.linkTo(txtTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            // Texto com o nível da prioridade (baixa, média ou alta)
            Text(
                text = priorityLevel,
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )

            // Pequeno card colorido que serve como indicador visual da prioridade
            Card(
                modifier = Modifier
                    .size(30.dp) // tamanho quadrado 30x30dp
                    .constrainAs(priorityCard) {
                        top.linkTo(txtDescription.bottom, margin = 10.dp)
                        start.linkTo(txtPriority.end, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                shape = ShapeCardPriority.large, // forma arredondada customizada
                colors = CardDefaults.cardColors(containerColor = colorPriority) // cor definida acima
            ) {}

            // Botão com ícone de lixeira para deletar a tarefa
            IconButton(
                onClick = {
                    openDialog.value = true // abre o diálogo de confirmação
                },
                modifier = Modifier.constrainAs(btDelete) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp)
                    start.linkTo(priorityCard.end, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = "Deletar" // descrição para acessibilidade
                )
            }
        }
    }
}
