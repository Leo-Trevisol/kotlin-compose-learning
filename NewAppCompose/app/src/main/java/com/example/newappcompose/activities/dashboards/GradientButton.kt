package com.example.newappcompose.activities.dashboards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.R

@Preview
@Composable
fun GradientButton( // Botão com fundo em gradiente
    onClick: () -> Unit = {}, // Ação ao clicar (padrão vazio)
    text: String = "Pesquisar", // Texto do botão (padrão)
    padding: Int = 0 // Padding externo (padrão zero)
) {
    Button( // Componente botão
        onClick = onClick, // Ação definida
        modifier = Modifier
            .fillMaxWidth() // Largura completa
            .padding(all = padding.dp), // Padding externo definido
        shape = RoundedCornerShape(size = 16.dp), // Bordas arredondadas
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Fundo transparente
    ) {
        Box( // Container interno com gradiente
            modifier = Modifier
                .background( // Fundo com gradiente
                    brush = Brush.linearGradient( // Gradiente linear
                        colors = listOf( // Lista de cores
                            colorResource(id = R.color.purple), // Roxo
                            colorResource(id = R.color.pink) // Rosa
                        )
                    ),
                    shape = RoundedCornerShape(size = 50.dp) // Bordas muito arredondadas
                )
                .fillMaxWidth() // Largura completa
                .padding(vertical = 12.dp), // Padding vertical interno
            contentAlignment = Alignment.Center // Centraliza conteúdo
        ) {
            Text( // Texto do botão
                text = text, // Texto definido
                color = Color.White, // Cor branca
                fontWeight = FontWeight.Bold, // Negrito
                fontSize = 18.sp // Tamanho da fonte
            )
        }
    }
}




