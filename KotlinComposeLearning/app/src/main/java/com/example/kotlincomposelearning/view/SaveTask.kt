package com.example.kotlincomposelearning.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTask(navController: NavController) {

    // Scaffold estrutura o layout da tela com áreas fixas como TopAppBar e FAB
    Scaffold(
        topBar = {
            // Barra superior com título customizado e cores personalizadas
            TopAppBar(
                title = {
                    Text(
                        text = "Save Task",
                        fontSize = 22.sp,           // Tamanho do título
                        fontWeight = FontWeight.Bold, // Negrito
                        textAlign = TextAlign.Start  // Alinhamento do texto
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2), // Cor de fundo da TopAppBar
                    titleContentColor = Color.White      // Cor do texto da TopAppBar
                )
            )
        }
    ) { innerPadding ->

        // Conteúdo principal da tela
        Box(
            modifier = Modifier
                .padding(innerPadding) // Evita sobreposição com TopAppBar e FAB
                .fillMaxSize()         // Ocupa toda a tela
                .background(Color(0xFFF5F5F5)) // Cor de fundo da área principal
        )
    }
}