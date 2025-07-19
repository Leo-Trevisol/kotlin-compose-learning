package com.example.newappcompose.activities.dashboards

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.newappcompose.R
import com.example.newappcompose.activities.search.SearchResultActivity
import com.example.newappcompose.domain.FlightModel
import com.example.newappcompose.domain.LocationModel
import com.example.newappcompose.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun YellowTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.lightPurple),
        modifier = modifier
    )
}

@Composable
fun PassengerSelector(
    adultCount: Int,
    childCount: Int,
    onAdultCountChange: (Int) -> Unit,
    onChildCountChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(text = "Passageiros", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Adult Selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .background(color = colorResource(id = R.color.lightPurple), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                IconButton(
                    onClick = { if (adultCount > 0) onAdultCountChange(adultCount - 1) },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Diminuir Adultos",
                        tint = colorResource(id = R.color.orange),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = "Adultos ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = "$adultCount",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )

                IconButton(
                    onClick = { onAdultCountChange(adultCount + 1) },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Aumentar Adultos",
                        tint = colorResource(id = R.color.orange),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Child Selector

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .background(color = colorResource(id = R.color.lightPurple), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = { if (childCount > 0) onChildCountChange(childCount - 1) },
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_minus),
                                contentDescription = "Diminuir Crianças",
                                tint = colorResource(id = R.color.orange),
                                modifier = Modifier.size(16.dp) // tamanho fixo do ícone
                            )
                        }

                        Text(
                            text = "Crianças ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "$childCount",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )

                        IconButton(
                            onClick = { onChildCountChange(childCount + 1) },
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_plus),
                                contentDescription = "Aumentar Crianças",
                                tint = colorResource(id = R.color.orange),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val locations = remember { mutableStateListOf<LocationModel>() }
    val viewModel = MainViewModel()
    var showLocationLoading by remember { mutableStateOf(true) }

    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var adultPassenger by remember { mutableStateOf(1) }
    var childPassenger by remember { mutableStateOf(0) }
    var departureDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var classes by remember { mutableStateOf("") }
    var showDepartureDatePicker by remember { mutableStateOf(false) }
    var showReturnDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var flights by remember { mutableStateOf<List<FlightModel>>(emptyList()) }

    // Carrega as localizações do Firebase
    LaunchedEffect(Unit) {
        viewModel.loadLocations().observeForever { result ->
            locations.clear()
            locations.addAll(result)
            showLocationLoading = false
            if (locations.isNotEmpty()) {
                from = locations[0].Name
                to = if (locations.size > 1) locations[1].Name ?: "" else ""
            }
        }
    }

    // Estado para os DatePickers
    val departureDatePickerState = rememberDatePickerState()
    val returnDatePickerState = rememberDatePickerState()

    // Formata a data selecionada
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    LaunchedEffect(departureDatePickerState.selectedDateMillis) {
        departureDatePickerState.selectedDateMillis?.let {
            departureDate = dateFormatter.format(Date(it))
        }
    }
    LaunchedEffect(returnDatePickerState.selectedDateMillis) {
        returnDatePickerState.selectedDateMillis?.let {
            returnDate = dateFormatter.format(Date(it))
        }
    }

    Scaffold(
        bottomBar = { MyBottomBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.lightPurple))
                .padding(paddingValues)
        ) {
            item {
                TopBar()
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .background(
                            color = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // From Dropdown
                    DropDownList(
                        items = locations.map { it.Name },
                        loadingIcon = painterResource(id = R.drawable.ic_airplane),
                        hint = "Selecione a partida",
                        iconTint = colorResource(id = R.color.orange),
                        showLocationLoading = showLocationLoading,
                        onItemSelected = { selected ->
                            from = selected
                        }
                    )

                    // To Dropdown
                    DropDownList(
                        items = locations.map { it.Name },
                        loadingIcon = painterResource(id = R.drawable.ic_airplane),
                        hint = "Selecione o destino",
                        iconTint = colorResource(id = R.color.orange),
                        showLocationLoading = showLocationLoading,
                        onItemSelected = { selected ->
                            to = selected
                        }
                    )

                    // Passengers
                    PassengerSelector(
                        adultCount = adultPassenger,
                        childCount = childPassenger,
                        onAdultCountChange = { adultPassenger = it },
                        onChildCountChange = { childPassenger = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Dates
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            YellowTitle(text = "Data de partida")
                            Button(
                                onClick = { showDepartureDatePicker = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.lightPurple),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (departureDate.isEmpty()) "Selecionar" else departureDate,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_calendar), // seu ícone de calendário
                                        contentDescription = "Ícone de calendário",
                                        tint = colorResource(id = R.color.orange),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            YellowTitle(text = "Data de retorno")
                            Button(
                                onClick = { showReturnDatePicker = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.lightPurple),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (returnDate.isEmpty()) "Selecionar" else returnDate,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_calendar), // seu ícone de calendário
                                        contentDescription = "Ícone de calendário",
                                        tint = colorResource(id = R.color.orange),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Class Dropdown
                    DropDownList(
                        items = listOf("Classe Econômica", "Classe Executiva", "Primeira Classe"),
                        loadingIcon = painterResource(id = R.drawable.ic_bell),
                        hint = "Selecione a Classe",
                        iconTint = colorResource(id = R.color.orange),
                        showLocationLoading = false,
                        onItemSelected = { selected ->
                            classes = selected
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Procurar Button
                    GradientButton(
                        onClick = {
                            viewModel.loadFiltered(from, to).observeForever { result ->
                                flights = result
                                val intent = Intent(context, SearchResultActivity::class.java).apply {
                                    putExtra("from", from)
                                    putExtra("to", to)
                                    putExtra("numPassenger", adultPassenger + childPassenger)
                                    putExtra("departureDate", departureDate)
                                    putExtra("returnDate", returnDate)
                                    putExtra("class", classes)
                                    putExtra("flights", ArrayList(flights))
                                    putExtra("fromShort", locations.find { it.Name == from }?.let { it.Id.toString() } ?: "")
                                    putExtra("toShort", locations.find { it.Name == to }?.let { it.Id.toString() } ?: "")
                                }
                                context.startActivity(intent)
                            }
                        },
                        text = "Procurar"
                    )
                }
            }
        }

        // DatePicker para Data de Partida
        if (showDepartureDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDepartureDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        showDepartureDatePicker = false
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDepartureDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = departureDatePickerState)
            }
        }

        // DatePicker para Data de Retorno
        if (showReturnDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showReturnDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        showReturnDatePicker = false
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showReturnDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = returnDatePickerState)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}