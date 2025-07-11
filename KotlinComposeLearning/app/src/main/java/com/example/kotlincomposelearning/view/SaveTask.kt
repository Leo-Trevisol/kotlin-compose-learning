package com.example.kotlincomposelearning.view

// Importações principais
import TextBox
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kotlincomposelearning.components.CustomButton
import com.example.kotlincomposelearning.constants.Constants
import com.example.kotlincomposelearning.repository.TasksRepository
import com.example.kotlincomposelearning.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTask(navController: NavController) {

    // Escopo da coroutine para chamadas assíncronas
    val scope = rememberCoroutineScope()

    // Contexto usado para mostrar o Toast
    val context = LocalContext.current

    // Instância do repositório de tarefas (poderia ser injetada via DI também)
    val tasksRepository = TasksRepository()

    // ---------------------- Estados da tela ----------------------

    // Título da tarefa (input do usuário)
    var taskTitle by remember { mutableStateOf("") }

    // Descrição da tarefa (input do usuário)
    var taskDesc by remember { mutableStateOf("") }

    // Prioridade selecionada (low, medium, high, ou null)
    var selectedPriority by remember { mutableStateOf<String?>(null) }

    // ---------------------- Layout da Tela ----------------------

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Save Task",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),  // Cor de fundo da AppBar
                    titleContentColor = Color.White      // Cor do texto na AppBar
                )
            )
        }
    ) { innerPadding ->

        // Coluna principal da tela
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))  // Fundo cinza claro
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Scroll para telas menores
        ) {

            // Campo de texto para o título
            TextBox(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Task Title",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            // Campo de texto para a descrição
            TextBox(
                value = taskDesc,
                onValueChange = { taskDesc = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = "Task Description",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )

            // Seção de seleção de prioridade (com RadioButtons)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Priority Level",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Prioridade baixa
                RadioButton(
                    selected = selectedPriority == "low",
                    onClick = { selectedPriority = "low" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_GREEN_ENABLED,
                        unselectedColor = RADIO_BUTTON_GREEN_DISABLED
                    )
                )

                // Prioridade média
                RadioButton(
                    selected = selectedPriority == "medium",
                    onClick = { selectedPriority = "medium" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_YELLOW_ENABLED,
                        unselectedColor = RADIO_BUTTON_YELLOW_DISABLED
                    )
                )

                // Prioridade alta
                RadioButton(
                    selected = selectedPriority == "high",
                    onClick = { selectedPriority = "high" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_RED_ENABLED,
                        unselectedColor = RADIO_BUTTON_RED_DISABLED
                    )
                )
            }

            // Botão de ação principal para salvar a tarefa
            CustomButton(
                text = "Save Task",
                onClick = {
                    scope.launch {
                        // Validação: título é obrigatório
                        if (taskTitle.isBlank()) {
                            Toast.makeText(context, "Please fill in the task title!", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        // Define a prioridade baseada na escolha do usuário (ou sem prioridade)
                        val priority = when (selectedPriority) {
                            "low" -> Constants.LOW_PRIORITY
                            "medium" -> Constants.MEDIUM_PRIORITY
                            "high" -> Constants.HIGH_PRIORITY
                            else -> Constants.NO_PRIORITY
                        }

                        // Executa o salvamento da tarefa em uma thread de IO
                        launch(Dispatchers.IO) {
                            tasksRepository.saveTask(taskTitle, taskDesc, priority)
                        }

                        // Feedback ao usuário e volta para tela anterior
                        Toast.makeText(context, "Task saved successfully!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )
        }
    }
}
