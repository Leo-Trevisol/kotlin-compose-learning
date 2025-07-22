// Importações necessárias para o funcionamento da tela e dos componentes Compose
package com.example.newappcompose.activities.dashboards

import android.content.Intent // Para navegação entre telas
import android.os.Bundle // Para manipular dados do ciclo de vida da Activity
import android.widget.Toast
import androidx.activity.ComponentActivity // Classe base para activities com Jetpack Compose
import androidx.activity.compose.setContent // Define o conteúdo da activity usando Compose
import androidx.activity.enableEdgeToEdge // Permite que o conteúdo ocupe a tela toda
import androidx.compose.foundation.Image // Componente para exibir imagens
import androidx.compose.foundation.background // Permite aplicar cor de fundo
import androidx.compose.foundation.layout.* // Componentes de layout como Column, Row, Spacer
import androidx.compose.foundation.lazy.LazyColumn // Lista otimizada para muitos itens
import androidx.compose.foundation.shape.RoundedCornerShape // Define cantos arredondados
import androidx.compose.material3.* // Componentes de Material 3 como Button, Text, etc
import androidx.compose.runtime.* // Gerenciamento de estado e efeitos colaterais
import androidx.compose.ui.Alignment // Alinhamento de componentes
import androidx.compose.ui.Modifier // Modificador usado para configurar UI
import androidx.compose.ui.graphics.Color // Cores
import androidx.compose.ui.res.colorResource // Acessa cor do resources (colors.xml)
import androidx.compose.ui.res.painterResource // Acessa drawable como recurso
import androidx.compose.ui.text.font.FontWeight // Peso da fonte (negrito, etc)
import androidx.compose.ui.unit.dp // Define valores de dimensões (padding, size, etc)
import androidx.compose.ui.unit.sp // Define valores de tamanho de fonte
import androidx.compose.ui.platform.LocalContext // Contexto da activity atual
import androidx.compose.ui.tooling.preview.Preview // Permite visualizar o Composable no preview
import com.example.newappcompose.R // Acessa recursos como drawables, strings, cores
import com.example.newappcompose.activities.search.SearchResultActivity // Tela de resultado de busca
import com.example.newappcompose.domain.FlightModel // Modelo de voo
import com.example.newappcompose.domain.LocationModel // Modelo de localidade
import com.example.newappcompose.viewmodel.MainViewModel // ViewModel principal
import com.google.gson.Gson
import java.text.SimpleDateFormat // Para formatação de data
import java.util.Date // Para trabalhar com datas
import java.util.Locale // Para definição de localização da data

// Activity principal do dashboard
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama a função da superclasse
        enableEdgeToEdge() // Usa o espaço total da tela
        setContent {
            MainScreen() // Define o conteúdo como a tela principal
        }
    }
}

// Composable reutilizável para título amarelo
@Composable
fun YellowTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, // Define o texto
        fontWeight = FontWeight.SemiBold, // Define o peso da fonte
        color = colorResource(id = R.color.lightPurple), // Define a cor do texto
        modifier = modifier // Modificador customizável
    )
}

// Componente que permite selecionar quantidade de passageiros adultos e crianças
@Composable
fun PassengerSelector(
    adultCount: Int, // Quantidade de adultos
    childCount: Int, // Quantidade de crianças
    onAdultCountChange: (Int) -> Unit, // Callback para alteração de adultos
    onChildCountChange: (Int) -> Unit // Callback para alteração de crianças
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Ocupa largura máxima
            .padding(top = 8.dp) // Espaçamento superior
    ) {
        Text(
            text = "Passageiros", // Título da seção
            fontWeight = FontWeight.Bold, // Negrito
            modifier = Modifier.padding(bottom = 8.dp) // Espaçamento inferior
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinha verticalmente ao centro
            modifier = Modifier.fillMaxWidth() // Ocupa largura total
        ) {
            // Seletor de adultos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f) // Ocupa metade da largura disponível
                    .background(
                        color = colorResource(id = R.color.lightPurple),
                        shape = RoundedCornerShape(8.dp)
                    ) // Fundo roxo com cantos arredondados
                    .padding(horizontal = 8.dp, vertical = 4.dp) // Padding interno
            ) {
                IconButton(
                    onClick = { if (adultCount > 0) onAdultCountChange(adultCount - 1) }, // Diminui adultos se maior que 0
                    modifier = Modifier.padding(end = 4.dp) // Espaço à direita
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus), // Ícone de menos
                        contentDescription = "Diminuir Adultos", // Descrição para acessibilidade
                        tint = colorResource(id = R.color.orange), // Cor laranja
                        modifier = Modifier.size(16.dp) // Tamanho do ícone
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
                    onClick = { onAdultCountChange(adultCount + 1) }, // Aumenta número de adultos
                    modifier = Modifier.padding(start = 4.dp) // Espaço à esquerda
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus), // Ícone de mais
                        contentDescription = "Aumentar Adultos", // Descrição acessível
                        tint = colorResource(id = R.color.orange), // Cor do ícone
                        modifier = Modifier.size(16.dp) // Tamanho do ícone
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp)) // Espaço entre adulto e criança

            // Seletor de crianças
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f) // Ocupa metade da largura
                    .background(
                        color = colorResource(id = R.color.lightPurple),
                        shape = RoundedCornerShape(8.dp)
                    ) // Fundo roxo com cantos arredondados
                    .padding(horizontal = 8.dp, vertical = 4.dp) // Espaçamento interno
            ) {
                IconButton(
                    onClick = { if (childCount > 0) onChildCountChange(childCount - 1) }, // Diminui número de crianças
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus), // Ícone de menos
                        contentDescription = "Diminuir Crianças", // Descrição acessível
                        tint = colorResource(id = R.color.orange), // Cor do ícone
                        modifier = Modifier.size(16.dp) // Tamanho fixo do ícone
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
                    onClick = { onChildCountChange(childCount + 1) }, // Aumenta número de crianças
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

@OptIn(ExperimentalMaterial3Api::class) // Indica que estamos usando APIs experimentais do Material3
@Composable // Indica que esta função é um componente de UI
fun MainScreen() {

    val locations =
        remember { mutableStateListOf<LocationModel>() } // Lista reativa com as localidades disponíveis
    val viewModel = MainViewModel() // ViewModel que carrega os dados da tela
    var showLocationLoading by remember { mutableStateOf(true) } // Indica se os locais ainda estão sendo carregados

    var from by remember { mutableStateOf("") } // Local de origem selecionado
    var to by remember { mutableStateOf("") } // Local de destino selecionado
    var adultPassenger by remember { mutableStateOf(1) } // Número de adultos
    var childPassenger by remember { mutableStateOf(0) } // Número de crianças
    var departureDate by remember { mutableStateOf("") } // Data de partida
    var returnDate by remember { mutableStateOf("") } // Data de retorno
    var classes by remember { mutableStateOf("") } // Classe selecionada
    var showDepartureDatePicker by remember { mutableStateOf(false) } // Exibe seletor de data de partida
    var showReturnDatePicker by remember { mutableStateOf(false) } // Exibe seletor de data de retorno
    val context = LocalContext.current // Obtém o contexto atual da Activity
    var flights by remember { mutableStateOf<List<FlightModel>>(emptyList()) } // Lista de voos encontrados

    LaunchedEffect(Unit) {
        viewModel.loadLocations().observeForever { result ->
            locations.clear() // Limpa lista atual
            locations.addAll(result) // Adiciona as localizações recebidas
            showLocationLoading = false // Finaliza loading
            if (locations.isNotEmpty()) {
                from = locations[0].Name // Define o local de partida padrão
                to = if (locations.size > 1) locations[1].Name
                    ?: "" else "" // Define o destino padrão
            }
        }
    }
    val departureDatePickerState = rememberDatePickerState() // Estado do seletor de data de partida
    val returnDatePickerState = rememberDatePickerState() // Estado do seletor de data de retorno

    val dateFormatter = remember {
        SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )
    } // Formata datas no padrão BR

    LaunchedEffect(departureDatePickerState.selectedDateMillis) {
        departureDatePickerState.selectedDateMillis?.let {
            departureDate = dateFormatter.format(Date(it)) // Atualiza a string da data de partida
        }
    }

    LaunchedEffect(returnDatePickerState.selectedDateMillis) {
        returnDatePickerState.selectedDateMillis?.let {
            returnDate = dateFormatter.format(Date(it)) // Atualiza a string da data de retorno
        }
    }

    Scaffold(
        bottomBar = { MyBottomBar() } // Define uma barra inferior
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda a tela
                .background(colorResource(id = R.color.lightPurple)) // Cor de fundo roxa
                .padding(paddingValues) // Adiciona padding interno do Scaffold
        ) {

            item {
                TopBar() // Componente de topo da tela
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(all = 16.dp) // Padding externo
                        .background(
                            color = colorResource(id = R.color.white), // Fundo branco
                            shape = RoundedCornerShape(16.dp) // Cantos arredondados
                        )
                        .fillMaxWidth() // Ocupa largura total
                        .padding(16.dp) // Padding interno
                ) {

                    DropDownList(
                        items = locations.map { it.Name }, // Lista de nomes de localidades
                        loadingIcon = painterResource(id = R.drawable.ic_airplane), // Ícone de avião
                        hint = "Selecione a partida", // Texto placeholder
                        iconTint = colorResource(id = R.color.orange), // Cor do ícone
                        showLocationLoading = showLocationLoading, // Mostra spinner se estiver carregando
                        onItemSelected = { selected ->
                            from = selected // Atualiza a origem
                        }
                    )

                    DropDownList(
                        items = locations.map { it.Name },
                        loadingIcon = painterResource(id = R.drawable.ic_airplane),
                        hint = "Selecione o destino",
                        iconTint = colorResource(id = R.color.orange),
                        showLocationLoading = showLocationLoading,
                        onItemSelected = { selected ->
                            to = selected // Atualiza destino
                        }
                    )

                    PassengerSelector(
                        adultCount = adultPassenger, // Passa o número atual de adultos
                        childCount = childPassenger, // Passa o número atual de crianças
                        onAdultCountChange = { adultPassenger = it }, // Atualiza estado
                        onChildCountChange = { childPassenger = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Espaço vertical

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Column(modifier = Modifier.weight(1f)) {
                            YellowTitle(text = "Data de partida") // Título da seção
                            Button(
                                onClick = { showDepartureDatePicker = true }, // Abre o DatePicker
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.lightPurple),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth(), // Ocupa largura total
                                shape = RoundedCornerShape(8.dp) // Cantos arredondados
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (departureDate.isEmpty()) "Selecionar" else departureDate, // Mostra data ou "Selecionar"
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f) // Ocupa espaço restante
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_calendar),
                                        contentDescription = "Ícone de calendário",
                                        tint = colorResource(id = R.color.orange),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre colunas

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
                                        painter = painterResource(id = R.drawable.ic_calendar),
                                        contentDescription = "Ícone de calendário",
                                        tint = colorResource(id = R.color.orange),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp)) // Espaçamento inferior

                    DropDownList(
                        items = listOf(
                            "Classe Econômica",
                            "Classe Executiva",
                            "Primeira Classe"
                        ), // Opções de classe
                        loadingIcon = painterResource(id = R.drawable.ic_bell), // Ícone decorativo
                        hint = "Selecione a Classe",
                        iconTint = colorResource(id = R.color.orange),
                        showLocationLoading = false,
                        onItemSelected = { selected ->
                            classes = selected // Atualiza a classe selecionada
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Espaçamento antes do botão

                    GradientButton(
                        onClick = {

                            // Validação
                            if (from.isEmpty() || to.isEmpty()) {
                                Toast.makeText(context, "Selecione origem e destino.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            if (from == to) {
                                Toast.makeText(context, "Origem e destino não podem ser iguais.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            if (adultPassenger < 1) {
                                Toast.makeText(context, "É necessário pelo menos 1 adulto.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            if (departureDate.isEmpty()) {
                                Toast.makeText(context, "Selecione a data de partida.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            if (returnDate.isEmpty()) {
                                Toast.makeText(context, "Selecione a data de retorno.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            if (classes.isEmpty()) {
                                Toast.makeText(context, "Selecione a classe desejada.", Toast.LENGTH_SHORT).show()
                                return@GradientButton
                            }

                            viewModel.loadFiltered(from, to).observeForever { result ->
                                flights = result // Atualiza lista de voos
                                val intent =
                                    Intent(context, SearchResultActivity::class.java).apply {
                                        putExtra("from", from)
                                        putExtra("to", to)
                                        putExtra("numPassenger", adultPassenger + childPassenger)
                                        putExtra("departureDate", departureDate)
                                        putExtra("returnDate", returnDate)
                                        putExtra("class", classes)
                                        val gson = Gson()
                                        val flightsJson = gson.toJson(flights)
                                        putExtra("flightsJson", flightsJson)
                                        putExtra(
                                            "fromShort",
                                            locations.find { it.Name == from }
                                                ?.let { it.Id.toString() } ?: "")
                                        putExtra(
                                            "toShort",
                                            locations.find { it.Name == to }
                                                ?.let { it.Id.toString() } ?: "")
                                    }
                                context.startActivity(intent) // Navega para a próxima tela
                            }
                        },
                        text = "Procurar" // Texto do botão
                    )
                }
            }
        }

        // Verifica se o seletor de data de partida deve ser exibido
        if (showDepartureDatePicker) {
            DatePickerDialog(
                // Ação ao clicar fora do diálogo ou pressionar "voltar"
                onDismissRequest = { showDepartureDatePicker = false },
                // Botão de confirmação
                confirmButton = {
                    Button(
                        onClick = {
                            showDepartureDatePicker = false // Fecha o diálogo ao confirmar
                        }
                    ) {
                        Text("Confirmar") // Texto exibido no botão de confirmação
                    }
                },
                // Botão de cancelamento
                dismissButton = {
                    Button(
                        onClick = {
                            showDepartureDatePicker = false // Fecha o diálogo ao cancelar
                        }
                    ) {
                        Text("Cancelar") // Texto exibido no botão de cancelamento
                    }
                }
            ) {
                DatePicker(state = departureDatePickerState) // Componente de seleção de data, vinculado ao estado da data de partida
            }
        }

        // Verifica se o seletor de data de retorno deve ser exibido
        if (showReturnDatePicker) {
            DatePickerDialog(
                // Ação ao clicar fora do diálogo ou pressionar "voltar"
                onDismissRequest = { showReturnDatePicker = false },
                // Botão de confirmação
                confirmButton = {
                    Button(
                        onClick = {
                            showReturnDatePicker = false // Fecha o diálogo ao confirmar
                        }
                    ) {
                        Text("Confirmar") // Texto exibido no botão de confirmação
                    }
                },
                // Botão de cancelamento
                dismissButton = {
                    Button(
                        onClick = {
                            showReturnDatePicker = false // Fecha o diálogo ao cancelar
                        }
                    ) {
                        Text("Cancelar") // Texto exibido no botão de cancelamento
                    }
                }
            ) {
                DatePicker(state = returnDatePickerState) // Componente de seleção de data, vinculado ao estado da data de retorno
            }
        }
    }
}


