package com.example.newappcompose.activities.dashboards

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar // Barra de navegação inferior (Material 3)
import androidx.compose.material3.NavigationBarItem // Item da barra de navegação
import androidx.compose.material3.Icon // Ícone do Material 3
import androidx.compose.runtime.* // remember, mutableStateOf, etc
import androidx.compose.ui.Modifier // Modificador para padding, size etc
import androidx.compose.ui.graphics.painter.Painter // Tipo para ícones
import androidx.compose.ui.platform.LocalContext // Para acessar o contexto atual
import androidx.compose.ui.res.colorResource // Para carregar cor do resources
import androidx.compose.ui.res.painterResource // Para carregar drawable como Painter
import androidx.compose.ui.tooling.preview.Preview // Preview para Compose
import androidx.compose.ui.unit.dp // Unidade de medida dp
import com.example.newappcompose.R // Recursos (cores, drawables, etc)

// Classe que representa um item do menu inferior
class BottomMenuItem(
    val label: String, // Nome/texto do item
    val icon: Painter  // Ícone (painter)
)

// Função que retorna a lista de itens da barra inferior
@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = "Home", icon = painterResource(id = R.drawable.ic_house_user)), // Item Home
        BottomMenuItem(label = "Cart", icon = painterResource(id = R.drawable.ic_compass)), // Item Carrinho
        BottomMenuItem(label = "Favorite", icon = painterResource(id = R.drawable.ic_bookmark)), // Item Favoritos
        BottomMenuItem(label = "Order", icon = painterResource(id = R.drawable.ic_user)) // Item Pedidos
    )
}

// Função principal que exibe a barra inferior
@Composable
@Preview
fun MyBottomBar() {
    val bottomMenuItemsList = prepareBottomMenu() // Lista de itens do menu
    val context = LocalContext.current // Contexto da aplicação
    var selectedItem by remember { mutableStateOf("Home") } // Item selecionado (estado)

    NavigationBar( // Barra inferior (Material 3)
        containerColor = colorResource(id = R.color.darkPurple2), // Cor de fundo
        tonalElevation = 3.dp // Sombra/elevação
    ) {
        bottomMenuItemsList.forEach { bottomMenuItem ->
            NavigationBarItem( // Cada item do menu
                selected = (selectedItem == bottomMenuItem.label), // Verifica se está selecionado
                onClick = { // Ação ao clicar
                    selectedItem = bottomMenuItem.label // Atualiza o item selecionado
                    if (bottomMenuItem.label == "Cart") {
                        // Ação para item "Cart" (pode ser abrir tela, etc)
                    } else {
                        // Mostra um toast com o nome do item
                        Toast.makeText(
                            context,
                            bottomMenuItem.label,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                icon = { // Ícone do item
                    Icon(
                        painter = bottomMenuItem.icon, // Ícone vindo da lista
                        contentDescription = null, // Sem descrição de acessibilidade
                        tint = colorResource(id = R.color.orange), // Cor do ícone
                        modifier = Modifier
                            .padding(top = 8.dp) // Espaço acima do ícone
                            .size(20.dp) // Tamanho do ícone
                    )
                }
            )
        }
    }
}
