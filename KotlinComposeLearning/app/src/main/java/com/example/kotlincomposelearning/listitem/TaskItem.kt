package com.example.kotlincomposelearning.listitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.kotlincomposelearning.R
import com.example.kotlincomposelearning.model.Task
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_GREEN_ENABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_RED_ENABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_YELLOW_ENABLED
import com.example.kotlincomposelearning.ui.theme.ShapeCardPriority

@Composable
fun TaskItem(
    position: Int = 0, // posição do item na lista, usada para pegar a tarefa correta
    taskList : MutableList<Task> // lista de tarefas de onde será extraído o item a ser exibido
) {

    // Pegando dados da tarefa na posição informada, ou definindo valores padrão caso estejam nulos
    val taskTitle = taskList[position].task ?: "Task ${position + 1}" // título da tarefa
    val taskDescription = taskList[position].description ?: "Description" // descrição da tarefa
    val taskPriority = taskList[position].priority ?: 3 // prioridade da tarefa, padrão 3 (alta)

    // Traduzindo o número da prioridade em texto legível
    var priorityLevel : String = when (taskPriority) {
        0 -> "Low priority"      // baixa prioridade
        1 -> "Medium priority"   // prioridade média
        2 -> "High priority"     // alta prioridade
        else -> "High priority"  // qualquer outro valor, considera alta prioridade
    }

    // Definindo a cor associada à prioridade para uso no indicador visual
    val colorPriority = when(taskPriority) {
        0 -> RADIO_BUTTON_GREEN_ENABLED   // verde para baixa prioridade
        1 -> RADIO_BUTTON_YELLOW_ENABLED  // amarelo para média prioridade
        2 -> RADIO_BUTTON_RED_ENABLED     // vermelho para alta prioridade
        else -> Black                     // preto para valores inválidos ou padrão
    }

    // Card container que engloba todo o item da tarefa
    Card(
        colors = CardDefaults.cardColors(containerColor = White), // fundo branco para o card
        modifier = Modifier
            .fillMaxWidth() // ocupa toda a largura disponível
            .padding(10.dp) // margem externa de 10dp ao redor do card
    ) {
        // Layout com restrições para posicionar os elementos dentro do card
        ConstraintLayout(
            modifier = Modifier.padding(20.dp) // padding interno de 20dp para afastar conteúdo das bordas do card
        ) {
            // Criando referências para cada elemento a ser posicionado no ConstraintLayout
            val (txtTitle, txtDescription, priorityCard, txtPriority, btDelete) = createRefs()

            // Texto para o título da tarefa
            Text(
                text = taskTitle,
                modifier = Modifier.constrainAs(txtTitle) {
                    top.linkTo(parent.top, margin = 10.dp)    // posiciona no topo com margem de 10dp
                    start.linkTo(parent.start, margin = 10.dp) // alinha à esquerda com margem de 10dp
                }
            )

            // Texto para a descrição da tarefa
            Text(
                text = taskDescription,
                modifier = Modifier.constrainAs(txtDescription) {
                    top.linkTo(txtTitle.bottom, margin = 10.dp) // abaixo do título com margem de 10dp
                    start.linkTo(parent.start, margin = 10.dp)   // alinhado à esquerda com margem
                }
            )

            // Texto para o nível de prioridade da tarefa
            Text(
                text = priorityLevel,
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp) // abaixo da descrição
                    start.linkTo(parent.start, margin = 10.dp)      // alinhado à esquerda
                    bottom.linkTo(parent.bottom, margin = 10.dp)    // alinhado ao fundo com margem
                }
            )

            // Card pequeno colorido que indica visualmente a prioridade da tarefa
            Card(
                modifier = Modifier
                    .size(30.dp) // tamanho fixo 30x30 dp
                    .constrainAs(priorityCard) {
                        top.linkTo(txtDescription.bottom, margin = 10.dp)     // abaixo da descrição
                        start.linkTo(txtPriority.end, margin = 10.dp)     // ao lado direito do texto da prioridade
                        bottom.linkTo(parent.bottom, margin = 10.dp)        // alinhado ao fundo
                    },
                shape = ShapeCardPriority.large,                          // forma customizada para bordas
                colors = CardDefaults.cardColors(containerColor = colorPriority) // cor de fundo conforme prioridade
            ) {
                // Conteúdo opcional do card - vazio neste caso
            }

            // Botão com ícone para deletar a tarefa
            IconButton(
                onClick = {
                    // Ação ao clicar no botão, por exemplo, deletar a tarefa da lista
                },
                modifier = Modifier.constrainAs(btDelete) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp)           // alinhado verticalmente com a prioridade
                    start.linkTo(priorityCard.end, margin = 30.dp)          // após o card colorido com distância maior
                    end.linkTo(parent.end, margin = 10.dp)                     // alinhado ao final do container com margem
                    bottom.linkTo(parent.bottom, margin = 10.dp)               // alinhado ao fundo com margem
                }
            ) {
                // Ícone do botão (imagem vetor)
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete), // ícone delete
                    contentDescription = null // descrição para acessibilidade (aqui omitida)
                )
            }
        }
    }
}

@Preview(showBackground = true) // permite visualizar no preview do Android Studio
@Composable
fun TaskItemPreview() {
    val mockTasks = mutableListOf(
        Task(
            task = "Buy some milk",                    // título de exemplo
            description = "Go to the market to buy milk", // descrição de exemplo
            priority = 2                               // prioridade alta para exemplo
        )
    )

    TaskItem(position = 0, taskList = mockTasks) // chama o composable com os dados mockados
}
