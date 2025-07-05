package com.example.kotlincomposelearning.view

import TextBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kotlincomposelearning.components.CustomButton
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_GREEN_DISABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_GREEN_ENABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_RED_DISABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_RED_ENABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_YELLOW_DISABLED
import com.example.kotlincomposelearning.ui.theme.RADIO_BUTTON_YELLOW_ENABLED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTask(navController: NavController) {

    // Estado reativo que armazena o título da tarefa
    // 'remember' garante que o valor persista durante a vida do Composable
    // 'mutableStateOf' faz com que, ao mudar o valor, o Compose redesenhe o que for necessário
    var taskTitle by remember { mutableStateOf("") }

    // Estado da descrição da tarefa
    var taskDesc by remember { mutableStateOf("") }

    // Estado que guarda qual prioridade está selecionada (low, medium, high ou null)
    // Apenas um RadioButton pode estar marcado, pois todos usam esse mesmo estado
    var selectedPriority by remember { mutableStateOf<String?>(null) }

    // Scaffold é a estrutura padrão do Compose para criar telas com TopAppBar, FAB, etc.
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
                    containerColor = Color(0xFF1976D2),  // Azul no topo da tela
                    titleContentColor = Color.White      // Cor do texto da barra superior
                )
            )
        }
    ) { innerPadding ->

        // Conteúdo da tela dentro de uma Column, com scroll vertical se necessário
        Column(
            modifier = Modifier
                .padding(innerPadding)  // Evita sobreposição do conteúdo com a TopAppBar
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))  // Fundo cinza claro
                .padding(16.dp)
                .verticalScroll(rememberScrollState())  // Permite rolar a tela
        ) {

            // Campo de texto customizado para o título da tarefa
            TextBox(
                value = taskTitle,  // Conteúdo atual do campo
                onValueChange = { taskTitle = it },  // Atualiza o estado ao digitar
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Task Title",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            // Campo de texto customizado para descrição, permite múltiplas linhas
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

            // Linha horizontal com o texto e os botões de prioridade
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

                // RadioButton de baixa prioridade
                RadioButton(
                    selected = selectedPriority == "low",  // Selecionado se for igual ao estado
                    onClick = { selectedPriority = "low" },  // Atualiza o estado ao clicar
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_GREEN_ENABLED,
                        unselectedColor = RADIO_BUTTON_GREEN_DISABLED
                    )
                )

                // RadioButton de prioridade média
                RadioButton(
                    selected = selectedPriority == "medium",
                    onClick = { selectedPriority = "medium" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_YELLOW_ENABLED,
                        unselectedColor = RADIO_BUTTON_YELLOW_DISABLED
                    )
                )

                // RadioButton de alta prioridade
                RadioButton(
                    selected = selectedPriority == "high",
                    onClick = { selectedPriority = "high" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RADIO_BUTTON_RED_ENABLED,
                        unselectedColor = RADIO_BUTTON_RED_DISABLED
                    )
                )
            }

            // Botão personalizado para salvar a tarefa
            CustomButton(
                text = "Save Task",
                onClick = {
                    // Aqui você pode salvar em banco, enviar para API ou validar os campos
                    println("Task Saved: $taskTitle, $taskDesc, Priority: $selectedPriority")

                    // Após salvar, volta para a tela de listagem
                    navController.navigate("listTasks")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )
        }
    }
}
