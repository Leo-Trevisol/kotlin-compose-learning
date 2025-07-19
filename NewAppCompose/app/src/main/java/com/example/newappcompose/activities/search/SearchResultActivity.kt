package com.example.newappcompose.activities.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class SearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Extract intent data
        val from = intent.getStringExtra("from") ?: ""
        val to = intent.getStringExtra("to") ?: ""
        val departureDate = intent.getStringExtra("departureDate") ?: ""
        val flightClass = intent.getStringExtra("class") ?: ""
        val fromShort = intent.getStringExtra("fromShort") ?: ""
        val toShort = intent.getStringExtra("toShort") ?: ""
        val flightsList = intent.getSerializableExtra("flights") as? ArrayList<FlightModel> ?: arrayListOf()

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
    val viewModel = MainViewModel()
    var flights by remember { mutableStateOf(initialFlights) }

    // Carrega os voos filtrados do Firebase com base nos parâmetros
    LaunchedEffect(Unit) {
        viewModel.loadFiltered(from, to).observeForever { result ->
            flights = result.filter { it.Date == departureDate && it.ClassSeat == flightClass }
        }
    }

    // Assume que pegamos o primeiro voo filtrado para exibir
    val flight = flights.firstOrNull() ?: FlightModel()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.purple_200))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Escolha os assentos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Cartão de bilhete
            Column(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.pink),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "from", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "to", color = colorResource(id = R.color.white))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = fromShort, fontSize = 24.sp, color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_airplane), // Substitua pelo recurso correto
                        contentDescription = "Airplane Icon",
                        modifier = Modifier
                            .size(20.dp) // Tamanho do ícone
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = toShort, fontSize = 24.sp, color = colorResource(id = R.color.white))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = from, color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = to, color = colorResource(id = R.color.white))
                }
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

                // Informações do voo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Class", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = flight.ClassSeat, color = colorResource(id = R.color.white))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Airlines", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = flight.AirlineName, color = colorResource(id = R.color.white))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Price", color = colorResource(id = R.color.white))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "R$ ${flight.Price}", color = colorResource(id = R.color.white))
                }

                // Código de barras (placeholder)
                Image(
                    painter = painterResource(id = R.drawable.ic_airplane), // Substitua pelo recurso correto
                    contentDescription = "Barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Text(
                    text = flight.ReservedSeats.ifEmpty { "321654687" }, // Placeholder para código
                    color = colorResource(id = R.color.white)
                )

                // Botão Baixar Bilhete
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