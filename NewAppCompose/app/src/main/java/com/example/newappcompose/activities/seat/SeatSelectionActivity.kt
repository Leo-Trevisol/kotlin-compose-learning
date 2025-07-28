// Pacote da activity de seleção de assentos
package com.example.newappcompose.activities.seat

// Importações necessárias do Android e Compose
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.R
import com.example.newappcompose.activities.dashboards.GradientButton // Botão customizado com gradiente

// Activity principal para seleção de assentos
class SeatSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define o conteúdo da activity usando Jetpack Compose
        setContent {
            SeatSelectionScreen()
        }
    }
}

@SuppressLint("ContextCastToActivity") // Suprime aviso de cast do contexto
@Composable
fun SeatSelectionScreen() {
    val rows = ('A'..'F') // Linhas de assentos (A até F)
    val cols = (1..10) // Colunas de assentos (1 até 10)
    val selectedSeats = remember { mutableStateListOf<String>() } // Lista reativa de assentos selecionados
    val unavailableSeats = listOf("C6", "D6", "E6") // Assentos indisponíveis
    val activity = LocalContext.current as? Activity // Referência à activity atual

    // Layout em coluna para organizar a tela
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda a tela
            .background(Color(0xFF38B6FF)) // Cor de fundo azul clara
            .padding(16.dp), // Espaçamento interno
        horizontalAlignment = Alignment.CenterHorizontally // Alinhamento central horizontal
    ) {
        // Linha com botão de voltar e título
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinhamento vertical ao centro
            modifier = Modifier.fillMaxWidth() // Ocupa largura total
        ) {
            IconButton(onClick = { activity?.finish() }) { // Botão de voltar que finaliza a activity
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ícone de seta para voltar
                    contentDescription = "Voltar", // Descrição acessível
                    tint = Color.White // Cor branca
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Espaço entre o botão e o texto
            Text(
                text = "Selecionar assentos", // Título da tela
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço vertical

        // Subtítulo
        Text("Escolha os assentos", fontSize = 20.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp)) // Espaço vertical

<<<<<<< HEAD
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Frente do avião ✈️",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Assentos (como já está no seu código)
        cols.forEach { col ->
            // Adiciona espaçamento entre colunas 5 e 6
            if (col == 6) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(horizontalArrangement = Arrangement.Center) {
                rows.forEachIndexed { index, row ->
                    val seatId = "$row$col"
                    val isSelected = seatId in selectedSeats
                    val isUnavailable = seatId in unavailableSeats
=======
        // Loop para gerar colunas de assentos (1 até 10)
        cols.forEach { col ->
            Row(horizontalArrangement = Arrangement.Center) { // Cada linha representa uma coluna
                rows.forEachIndexed { index, row -> // Percorre as letras (linhas)
                    val seatId = "$row$col" // Ex: A1, B2, etc.
                    val isSelected = seatId in selectedSeats // Verifica se está selecionado
                    val isUnavailable = seatId in unavailableSeats // Verifica se está indisponível
>>>>>>> ea68217115665f5b00f2a83ded879e3813e0b3bc

                    if (index == 3) {
                        Spacer(modifier = Modifier.width(16.dp)) // Adiciona espaço para simular corredor entre bancos
                    }

                    // Caixa que representa cada assento
                    Box(
                        modifier = Modifier
                            .padding(4.dp) // Espaçamento ao redor
                            .size(40.dp) // Tamanho do quadrado
                            .background(
                                when { // Define cor com base no estado do assento
                                    isUnavailable -> Color.LightGray // Cinza se indisponível
                                    isSelected -> Color.Yellow // Amarelo se selecionado
                                    else -> Color.Green // Verde se disponível
                                },
                                shape = RoundedCornerShape(4.dp) // Cantos arredondados
                            )
                            .clickable(enabled = !isUnavailable) { // Só permite clique se estiver disponível
                                if (isSelected) selectedSeats.remove(seatId) // Remove se já estava selecionado
                                else selectedSeats.add(seatId) // Adiciona se ainda não estava
                            },
                        contentAlignment = Alignment.Center // Centraliza o texto
                    ) {
                        Text(text = seatId, fontSize = 12.sp, color = Color.Black) // Exibe o ID do assento
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // Espaço entre os assentos e a legenda

        // Linha com legenda das cores
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            LegendBox(Color.Green, "Disponível") // Verde = disponível
            LegendBox(Color.Yellow, "Selecionado") // Amarelo = selecionado
            LegendBox(Color.LightGray, "Indisponível") // Cinza = indisponível
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre legenda e botão

        // Botão para confirmar seleção dos assentos
        GradientButton(
            onClick = {
                // salvar seleção ou enviar resultado
            },
            text = "Confirmar assentos", // Texto do botão
            padding = 0 // Sem padding adicional
        )
    }
}

// Composable que exibe uma cor com um texto ao lado, usado para legenda
@Composable
fun LegendBox(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) { // Alinha verticalmente
        Box(
            modifier = Modifier
                .size(16.dp) // Tamanho do quadrado
                .background(color, shape = RoundedCornerShape(4.dp)) // Cor e bordas
        )
        Spacer(modifier = Modifier.width(4.dp)) // Espaço entre cor e texto
        Text(label, color = Color.White, fontSize = 12.sp) // Texto da legenda
    }
}
