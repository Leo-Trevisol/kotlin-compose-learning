package com.example.newappcompose.activities.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.R
import com.example.newappcompose.domain.FlightModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val from = intent.getStringExtra("from") ?: ""
        val to = intent.getStringExtra("to") ?: ""
        val departureDate = intent.getStringExtra("departureDate") ?: ""
        val flightClass = intent.getStringExtra("class") ?: ""
        val fromShort = intent.getStringExtra("fromShort") ?: ""
        val toShort = intent.getStringExtra("toShort") ?: ""
        val gson = Gson()
        val flightsJson = intent.getStringExtra("flightsJson")
        val type = object : TypeToken<List<FlightModel>>() {}.type
        val flightsList: List<FlightModel> = gson.fromJson(flightsJson, type)

        setContent {
            Scaffold(
                containerColor = colorResource(id = R.color.pink)
            ) { padding ->
                LazyColumn(
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    items(flightsList.size) { index ->
                        FlightCardStyled(
                            from = from,
                            to = to,
                            fromShort = fromShort,
                            toShort = toShort,
                            departureDate = departureDate,
                            flight = flightsList[index]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlightCardStyled(
    from: String,
    to: String,
    fromShort: String,
    toShort: String,
    departureDate: String,
    flight: FlightModel
) {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.white), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = fromShort, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "20", fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = toShort, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = from, fontSize = 12.sp, color = colorResource(id = R.color.grey))
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_airplane),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = to, fontSize = 12.sp, color = colorResource(id = R.color.grey))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = flight.classSeat, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = colorResource(id = R.color.orange))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = flight.airlineName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${flight.price}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
