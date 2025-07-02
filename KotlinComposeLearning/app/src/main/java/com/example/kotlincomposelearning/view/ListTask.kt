import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

// Indica que esta função faz parte da interface visual no Compose
//Marca funções responsáveis por criar UI no Compose.
//Pode ser combinada com outros Composables, criando interfaces completas.
//É gerenciada pelo Compose, que cuida de atualizar a tela automaticamente conforme o estado muda.
//Só pode ser chamada de dentro de outra função Composable ou dentro de setContent.
@Composable
fun ListTask(navController: NavController) {

    // Exibe um texto na tela
    Text(text = "List Task test")

    // Aqui se pode adicionar outros elementos, como botão para navegar
    // Exemplo:
    // Button(onClick = { navController.navigate("saveTasks") }) { Text("Ir para salvar tarefa") }
}
