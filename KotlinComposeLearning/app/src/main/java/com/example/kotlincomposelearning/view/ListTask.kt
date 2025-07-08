import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kotlincomposelearning.listitem.TaskItem
import com.example.kotlincomposelearning.model.Task

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTask(navController: NavController) {

    // Scaffold organiza a estrutura básica da tela:
    // inclui TopAppBar, FloatingActionButton e content (conteúdo principal)
    Scaffold(
        // TopAppBar fixa no topo com título
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tasks",
                        fontSize = 22.sp,               // Tamanho da fonte do título
                        fontWeight = FontWeight.Bold,   // Título em negrito
                        textAlign = TextAlign.Start     // Alinhado à esquerda
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2), // Cor de fundo azul da AppBar
                    titleContentColor = Color.White     // Cor branca do texto
                )
            )
        },

        // Botão flutuante de ação (FAB) no canto inferior direito
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Ao clicar, navega para a tela de adicionar tarefa
                    navController.navigate("saveTasks")
                },
                containerColor = Color(0xFF1976D2) // Cor do botão FAB
            ) {
                // Ícone "+" dentro do botão
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = Color.White // Cor branca do ícone
                )
            }
        }
    ) { innerPadding -> // innerPadding previne que o conteúdo fique por baixo da TopAppBar

        // Lista simulada de tarefas
        val taskList: MutableList<Task> = mutableListOf(
            Task(
                task = "Play soccer",
                description = "dfdsfsdfsdfsdfsdfsdfsdfds",
                priority = 0
            ),
            Task(
                task = "Go to the movies",
                description = "dfdsfsdfsdfsdfsdfsdfsdfds",
                priority = 1
            ),
            Task(
                task = "Go to the college",
                description = "dfdsfsdfsdfsdfsdfsdfsdfds",
                priority = 2
            ),
            Task(
                task = "Try on new clothes",
                description = "dfdsfsdfsdfsdfsdfsdfsdfds",
                priority = 3
            )
        )

        // LazyColumn: lista otimizada que renderiza itens sob demanda
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)          // Respeita espaço da AppBar e do FAB
                .background(Color(0xFFF5F5F5))   // Cor de fundo clara
                .fillMaxSize()                  // Ocupa todo o espaço disponível
        ) {
            // itemsIndexed permite acesso ao índice e item da lista
            itemsIndexed(taskList) { position, _ ->
                // Renderiza um item da tarefa (componente personalizado)
                TaskItem(position, taskList)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListTaskPreview() {
    // Preview usa um NavController falso apenas para renderização visual
    val navController = rememberNavController()
    ListTask(navController = navController)
}
