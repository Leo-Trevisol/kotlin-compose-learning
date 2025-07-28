package com.example.newappcompose.activities.ticket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.domain.FlightModel
import com.example.newappcompose.ui.theme.NewAppComposeTheme
import com.google.gson.Gson

// Activity que exibe o bilhete do voo com os detalhes recebidos
class TicketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama o onCreate da superclasse

        // Obtém o JSON do voo enviado pela intent
        val flightJson = intent.getStringExtra("flight")
        // Converte o JSON para o objeto FlightModel
        val flight = Gson().fromJson(flightJson, FlightModel::class.java)

        // Obtém o aeroporto de origem da intent, ou vazio se nulo
        val from = intent.getStringExtra("from") ?: ""
        // Obtém o aeroporto de destino da intent, ou vazio se nulo
        val to = intent.getStringExtra("to") ?: ""
        // Obtém a data do voo da intent, ou vazio se nulo
        val date = intent.getStringExtra("date") ?: ""
        // Obtém o horário do voo do objeto FlightModel
        val time = flight.time
        // Obtém o nome da companhia aérea do objeto FlightModel
        val airline = flight.airlineName
        // Obtém a classe do assento do objeto FlightModel
        val seatClass = flight.classSeat
        // Obtém o preço do voo do objeto FlightModel
        val price = flight.price
        // Obtém os assentos selecionados da intent, ou vazio se nulo
        val seats = intent.getStringExtra("seats") ?: ""
        // Obtém o código de barras da intent, ou vazio se nulo
        val barcode = intent.getStringExtra("barcode") ?: ""

        // Define o conteúdo da tela com Jetpack Compose
        setContent {
            // Aplica o tema da aplicação
            NewAppComposeTheme {
                // Chama o composable que exibe a tela do bilhete, passando os dados
                TicketScreen(
                    from = from,
                    to = to,
                    airline = airline,
                    date = date,
                    time = time,
                    seat = seats,
                    flightClass = seatClass,
                    price = price,
                    barcode = barcode
                )
            }
        }
    }
}

// Composable que representa a tela do bilhete com todos os detalhes do voo
@Composable
fun TicketScreen(
    from: String,
    to: String,
    airline: String,
    date: String,
    time: String,
    seat: String,
    flightClass: String,
    price: Double,
    barcode: String
) {

    // Layout vertical que preenche toda a tela, com fundo azul e padding
    Column(
        modifier = Modifier
            .fillMaxSize() // ocupa toda a tela
            .background(Color(0xFF38B6FF)) // cor de fundo azul
            .padding(16.dp), // espaçamento interno
        horizontalAlignment = Alignment.CenterHorizontally // centraliza filhos horizontalmente
    ) {
        // Texto fixo "Escolha os assentos"
        Text("Escolha os assentos", fontSize = 18.sp, color = Color.White)

        // Espaçamento vertical de 20dp
        Spacer(modifier = Modifier.height(20.dp))

        // Card que contém os detalhes do voo
        Card(
            modifier = Modifier
                .fillMaxWidth() // ocupa toda largura disponível
                .padding(8.dp), // espaçamento externo do card
            shape = RoundedCornerShape(16.dp), // cantos arredondados do card
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0057B8)) // cor azul escuro do card
        ) {
            // Coluna interna com padding para os detalhes do voo
            Column(modifier = Modifier.padding(16.dp)) {
                // Linha que mostra "From" e "To" distribuídos nas extremidades
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    // Coluna com label e valor do aeroporto de origem
                    Column {
                        Text("From", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(from, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    // Coluna alinhada à direita com label e valor do aeroporto de destino
                    Column(horizontalAlignment = Alignment.End) {
                        Text("To", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(to, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Espaçamento vertical de 12dp
                Spacer(modifier = Modifier.height(12.dp))

                // Linha que mostra "Date" e "Time"
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    // Coluna com data do voo
                    Column {
                        Text("Date", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(date, color = Color.White, fontSize = 14.sp)
                    }
                    // Coluna alinhada à direita com horário do voo
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Time", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(time, color = Color.White, fontSize = 14.sp)
                    }
                }

                // Espaçamento vertical de 12dp
                Spacer(modifier = Modifier.height(12.dp))

                // Linha que mostra "Class" e "Airlines"
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    // Coluna com classe do assento
                    Column {
                        Text("Class", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(flightClass, color = Color.White, fontSize = 14.sp)
                    }
                    // Coluna alinhada à direita com nome da companhia aérea
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Airlines", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(airline, color = Color.White, fontSize = 14.sp)
                    }
                }

                // Espaçamento vertical de 12dp
                Spacer(modifier = Modifier.height(12.dp))

                // Linha que mostra "Seats" e "Price"
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    // Coluna com assentos selecionados
                    Column {
                        Text("Seats", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text(seat, color = Color.White, fontSize = 14.sp)
                    }
                    // Coluna alinhada à direita com preço formatado
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Price", color = Color.White.copy(0.7f), fontSize = 12.sp)
                        Text("R$ $price", color = Color.White, fontSize = 14.sp)
                    }
                }

                // Espaçamento vertical de 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // Box que simula o código de barras, com fundo branco e cantos arredondados
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // largura total
                        .height(60.dp) // altura fixa
                        .background(Color.White, RoundedCornerShape(4.dp)), // fundo branco com cantos arredondados
                    contentAlignment = Alignment.Center // centraliza o conteúdo
                ) {
                    // Exibe o código de barras em texto preto
                    Text(barcode, fontSize = 16.sp, color = Color.Black)
                }
            }
        }

        // Espaçamento vertical de 20dp
        Spacer(modifier = Modifier.height(20.dp))

        // Botão para "Baixar o Bilhete"
        Button(
            onClick = { /* TODO: implementar baixar bilhete */ },
            shape = RoundedCornerShape(12.dp), // cantos arredondados do botão
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura disponível
                .height(48.dp) // altura fixa do botão
        ) {
            // Texto dentro do botão
            Text("Baixar o Bilhete")
        }
    }
}
