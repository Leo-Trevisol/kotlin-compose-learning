// Pacote onde o arquivo está localizado
package com.example.newappcompose.activities.dashboards

// Importações necessárias para composições e elementos da UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.R

// Permite o uso de APIs experimentais do Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    items: List<String>,                // Lista de opções para o menu suspenso
    loadingIcon: Any,                  // Ícone mostrado à esquerda do campo de texto
    hint: String,                      // Texto exibido como dica (placeholder)
    showLocationLoading: Boolean,     // Indica se o indicador de carregamento deve ser mostrado
    onItemSelected: (String) -> Unit, // Callback chamado ao selecionar um item
    iconTint: Color? = null           // Cor opcional do ícone
) {
    var expanded by remember { mutableStateOf(false) }           // Controla se o menu suspenso está expandido
    var selectedItem by remember { mutableStateOf(hint) }        // Armazena o item atualmente selecionado

    // Container do menu suspenso
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {

        // Campo de texto com borda para exibir o item selecionado
        OutlinedTextField(
            value = selectedItem,                     // Valor exibido no campo
            onValueChange = { selectedItem = it },    // Atualiza valor (embora esteja em modo somente leitura)
            readOnly = true,                          // Impede digitação direta
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))                             // Borda arredondada
                .background(colorResource(id = R.color.lightPurple))        // Fundo lilás
                .menuAnchor(),                                              // Âncora do menu suspenso
            placeholder = { // Dica quando o campo está vazio
                Text(
                    text = hint,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            leadingIcon = { // Ícone à esquerda do campo
                if (iconTint != null) {
                    // Se houver cor definida, aplica o filtro de cor
                    Image(
                        painter = loadingIcon as? Painter ?: painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
                    )
                } else {
                    // Caso contrário, exibe o ícone sem cor
                    Image(
                        painter = loadingIcon as? Painter ?: painterResource(id = R.drawable.ic_user),
                        contentDescription = null
                    )
                }
            },
            textStyle = TextStyle( // Estilo do texto no campo
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = OutlinedTextFieldDefaults.colors( // Cores personalizadas do campo
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // Exibe indicador de carregamento se necessário
        if (showLocationLoading) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.lightPurple),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .height(55.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Spinner de carregamento
            }
        } else {
            // Exibe o menu suspenso com as opções
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Fecha ao clicar fora
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        },
                        onClick = {
                            selectedItem = item      // Define item selecionado
                            expanded = false         // Fecha o menu
                            onItemSelected(item)     // Dispara o callback
                        }
                    )
                }
            }
        }
    }
}

// Função para exibir um preview do componente no Android Studio
@Preview(showBackground = true)
@Composable
fun DropDownListPreview() {
    DropDownList(
        items = listOf("NewYork", "Los Angeles"),                         // Lista de exemplo
        loadingIcon = painterResource(id = R.drawable.ic_airplane),      // Ícone exemplo
        hint = "Selecione a partida",                                    // Dica inicial
        showLocationLoading = false,                                     // Sem carregamento
        onItemSelected = {}                                              // Nenhuma ação no preview
    )
}
