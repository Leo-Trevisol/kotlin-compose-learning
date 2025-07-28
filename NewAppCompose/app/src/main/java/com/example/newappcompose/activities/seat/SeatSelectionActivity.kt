package com.example.newappcompose.activities.seat

// Importações necessárias
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import com.example.newappcompose.activities.dashboards.GradientButton
import com.example.newappcompose.activities.ticket.TicketActivity
import com.example.newappcompose.domain.FlightModel
import com.google.gson.Gson

// Activity que exibe a seleção de assentos
class SeatSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupera os dados passados pela intent
        val flightJson = intent.getStringExtra("flight") // Voo em JSON
        val flight = Gson().fromJson(flightJson, FlightModel::class.java) // Converte em objeto

        val from = intent.getStringExtra("from") ?: "" // Aeroporto de origem
        val to = intent.getStringExtra("to") ?: "" // Aeroporto de destino
        val departureDate = intent.getStringExtra("departureDate") ?: "" // Data do voo

        // Define o conteúdo da tela com Compose
        setContent {
            SeatSelectionScreen(flight, from, to, departureDate)
        }
    }
}

// Composable que exibe a tela de seleção de assentos
@SuppressLint("ContextCastToActivity")
@Composable
fun SeatSelectionScreen(
    flight: FlightModel,
    from: String,
    to: String,
    departureDate: String
) {
    val rows = ('A'..'F') // Letras das fileiras (linhas)
    val cols = (1..10) // Números das colunas
    val selectedSeats = remember { mutableStateListOf<String>() } // Lista de assentos selecionados
    val unavailableSeats = listOf("C6", "D6", "E6") // Assentos indisponíveis
    val activity = LocalContext.current as? Activity // Referência à activity atual
    val context = LocalContext.current // Contexto atual

    // Coluna principal da tela
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda a tela
            .background(Color(0xFF38B6FF)) // Cor de fundo azul
            .padding(16.dp), // Padding interno
        horizontalAlignment = Alignment.CenterHorizontally // Alinha horizontalmente ao centro
    ) {
        // Barra superior com botão de voltar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botão que finaliza a activity atual
            IconButton(onClick = { activity?.finish() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ícone de seta
                    contentDescription = "Voltar",
                    tint = Color.White // Cor branca
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Espaço entre ícone e texto
            Text(
                text = "Selecionar assentos",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço abaixo do título

        // Texto de instrução
        Text("Escolha os assentos", fontSize = 20.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp)) // Espaço abaixo do texto

        // Caixa indicando a frente do avião
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ),
            contentAlignment = Alignment.Center // Alinha o conteúdo ao centro
        ) {
            Text(
                text = "Frente do avião ✈️",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp)) // Espaço abaixo da frente do avião

        // Geração das fileiras e colunas de assentos
        cols.forEach { col ->
            // Adiciona espaço extra entre colunas 5 e 6 (para simular o corredor)
            if (col == 6) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(horizontalArrangement = Arrangement.Center) {
                rows.forEachIndexed { index, row ->
                    val seatId = "$row$col" // Ex: A1, B2...
                    val isSelected = seatId in selectedSeats // Verifica se está selecionado
                    val isUnavailable = seatId in unavailableSeats // Verifica se está indisponível

                    // Adiciona espaço entre colunas C e D (corredor central)
                    if (index == 3) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    // Assento individual
                    Box(
                        modifier = Modifier
                            .padding(4.dp) // Espaço ao redor do assento
                            .size(40.dp) // Tamanho do quadrado do assento
                            .background(
                                when {
                                    isUnavailable -> Color.LightGray // Indisponível
                                    isSelected -> Color.Yellow // Selecionado
                                    else -> Color.Green // Disponível
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable(enabled = !isUnavailable) { // Clique só se disponível
                                if (isSelected) selectedSeats.remove(seatId) // Remove da seleção
                                else selectedSeats.add(seatId) // Adiciona à seleção
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = seatId, fontSize = 12.sp, color = Color.Black) // Texto do assento
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // Espaço abaixo dos assentos

        // Legenda de cores
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            LegendBox(Color.Green, "Disponível")
            LegendBox(Color.Yellow, "Selecionado")
            LegendBox(Color.LightGray, "Indisponível")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço abaixo da legenda

        // Botão para confirmar assentos
        GradientButton(
            onClick = {
                // Cria intent para abrir a tela do ticket
                val intent = Intent(context, TicketActivity::class.java).apply {
                    putExtra("flight", Gson().toJson(flight)) // Voo em JSON
                    putExtra("from", from)
                    putExtra("to", to)
                    putExtra("date", departureDate)
                    putExtra("seats", selectedSeats.joinToString(",")) // Converte lista em string
                    putExtra("barcode", "321654687") // Pode ser gerado dinamicamente
                }
                context.startActivity(intent) // Inicia a nova Activity
            },
            text = "Confirmar assentos", // Texto do botão
            padding = 0
        )
    }
}

// Componente que mostra uma bolinha de legenda com texto ao lado
@Composable
fun LegendBox(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Quadradinho colorido
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(4.dp)) // Espaço entre cor e texto
        Text(label, color = Color.White, fontSize = 12.sp) // Texto da legenda
    }
}
