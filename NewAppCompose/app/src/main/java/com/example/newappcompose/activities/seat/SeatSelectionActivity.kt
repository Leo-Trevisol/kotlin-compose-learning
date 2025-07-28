package com.example.newappcompose.activities.seat

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
import com.example.newappcompose.activities.dashboards.GradientButton

class SeatSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SeatSelectionScreen()
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun SeatSelectionScreen() {
    val rows = ('A'..'F')
    val cols = (1..10)
    val selectedSeats = remember { mutableStateListOf<String>() }
    val unavailableSeats = listOf("C6", "D6", "E6")
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF38B6FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botão de voltar no topo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { activity?.finish() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Selecionar assentos",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Escolha os assentos", fontSize = 20.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

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

                    if (index == 3) {
                        Spacer(modifier = Modifier.width(16.dp)) // Corredor
                    }

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(40.dp)
                            .background(
                                when {
                                    isUnavailable -> Color.LightGray
                                    isSelected -> Color.Yellow
                                    else -> Color.Green
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable(enabled = !isUnavailable) {
                                if (isSelected) selectedSeats.remove(seatId)
                                else selectedSeats.add(seatId)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = seatId, fontSize = 12.sp, color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Legenda
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            LegendBox(Color.Green, "Disponível")
            LegendBox(Color.Yellow, "Selecionado")
            LegendBox(Color.LightGray, "Indisponível")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão com gradiente
        GradientButton(
            onClick = {
                // salvar seleção ou enviar resultado
            },
            text = "Confirmar assentos",
            padding = 0
        )
    }
}


@Composable
fun LegendBox(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, color = Color.White, fontSize = 12.sp)
    }
}

