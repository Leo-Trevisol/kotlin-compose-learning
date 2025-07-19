package com.example.newappcompose.activities.search

// Importações necessárias para a atividade e Compose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.R
import com.example.newappcompose.domain.FlightModel
import com.example.newappcompose.viewmodel.MainViewModel

// Activity que mostra os resultados da busca por voos
class SearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita conteúdo sob a barra de status

        // Recupera dados da Intent enviados de outra tela
        val from = intent.getStringExtra("from") ?: ""
        val to = intent.getStringExtra("to") ?: ""
        val departureDate = intent.getStringExtra("departureDate") ?: ""
        val flightClass = intent.getStringExtra("class") ?: ""
        val fromShort = intent.getStringExtra("fromShort") ?: ""
        val toShort = intent.getStringExtra("toShort") ?: ""
        val flightsList = intent.getSerializableExtra("flights") as? ArrayList<FlightModel> ?: arrayListOf()

        // Define o conteúdo da tela usando Compose
        setContent {
            SearchResultScreen(
                from = from,
                to = to,
                departureDate = departureDate,
                flightClass = flightClass,
                fromShort = fromShort,
                toShort = toShort,
                initialFlights = flightsList
            )
        }
    }
}

// Composable que representa a tela de resultado da busca
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    from: String,
    to: String,
    departureDate: String,
    flightClass: String,
    fromShort: String,
    toShort: String,
    initialFlights: List<FlightModel>
) {
    val viewModel = MainViewModel() // Instancia o ViewModel
    var flights by remember { mutableStateOf(initialFlights) } // Estado com a lista de voos

    // Carrega os voos do Firebase conforme filtros definidos
    LaunchedEffect(Unit) {
        viewModel.loadFiltered(from, to).observeForever { result ->
            flights = result.filter { it.Date == departureDate && it.ClassSeat == flightClass }
        }
    }

    val flight = flights.firstOrNull() ?: FlightModel() // Pega o primeiro voo encontrado (ou vazio)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.purple_200))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "Escolha os assentos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Cartão com informações do voo
            Column(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.pink),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Linha de origem e destino (labels)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "from", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "to", color = colorResource(id = R.color.white))
                }
                // Linha de origem e destino (siglas e ícone)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = fromShort, fontSize = 24.sp, color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_airplane),
                        contentDescription = "Airplane Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = toShort, fontSize = 24.sp, color = colorResource(id = R.color.white))
                }
                // Linha com nomes das cidades
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = from, color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = to, color = colorResource(id = R.color.white))
                }
                // Linha com data e horário
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Date", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Time", color = colorResource(id = R.color.white))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = departureDate, fontSize = 18.sp, color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = flight.ArriveTime, fontSize = 18.sp, color = colorResource(id = R.color.white))
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Linha divisória
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.white))
                )

                // Linha com classe do voo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Class", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = flight.ClassSeat, color = colorResource(id = R.color.white))
                }
                // Linha com companhia aérea
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Airlines", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = flight.AirlineName, color = colorResource(id = R.color.white))
                }
                // Linha com preço
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Price", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "R$ ${flight.Price}", color = colorResource(id = R.color.white))
                }

                // Placeholder para código de barras
                Image(
                    painter = painterResource(id = R.drawable.ic_airplane),
                    contentDescription = "Barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Text(
                    text = flight.ReservedSeats.ifEmpty { "321654687" },
                    color = colorResource(id = R.color.white)
                )

                // Botão de baixar bilhete
                Button(
                    onClick = { /* Lógica para baixar bilhete */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Baixar Bilhete", color = colorResource(id = R.color.white))
                }
            }
        }
    }
}
