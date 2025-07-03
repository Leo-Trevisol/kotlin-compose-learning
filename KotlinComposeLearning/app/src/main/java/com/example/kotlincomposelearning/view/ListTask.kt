import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 * Tela principal que exibe uma lista de tarefas (exemplo simples).
 * Possui TopAppBar, cor de fundo e um botão flutuante de ação (FAB).
 *
 * @param navController NavController usado para navegação entre telas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTask(navController: NavController) {

    // Scaffold estrutura o layout da tela com áreas fixas como TopAppBar e FAB
    Scaffold(
        topBar = {
            // Barra superior com título customizado e cores personalizadas
            TopAppBar(
                title = {
                    Text(
                        text = "Minhas Tarefas",
                        fontSize = 22.sp,           // Tamanho do título
                        fontWeight = FontWeight.Bold, // Negrito
                        textAlign = TextAlign.Start  // Alinhamento do texto
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2), // Cor de fundo da TopAppBar
                    titleContentColor = Color.White      // Cor do texto da TopAppBar
                )
            )
        },
        // Botão flutuante no canto inferior direito da tela
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Ação ao clicar no FAB, exemplo: navegação
                    navController.navigate("saveTasks")
                },
                containerColor = Color(0xFF1976D2) // Cor de fundo do FAB
            ) {
                // Ícone "+" dentro do FAB
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = Color.White // Cor do ícone
                )
            }
        }
    ) { innerPadding ->

        // Conteúdo principal da tela
        Box(
            modifier = Modifier
                .padding(innerPadding) // Evita sobreposição com TopAppBar e FAB
                .fillMaxSize()         // Ocupa toda a tela
                .background(Color(0xFFF5F5F5)) // Cor de fundo da área principal
        )
    }
}

/**
 * Função de Preview para visualizar o layout diretamente no Android Studio.
 * Cria um NavController fake apenas para fins de visualização.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListTaskPreview() {
    val navController = rememberNavController() // Mock do NavController para o Preview
    ListTask(navController = navController)
}
