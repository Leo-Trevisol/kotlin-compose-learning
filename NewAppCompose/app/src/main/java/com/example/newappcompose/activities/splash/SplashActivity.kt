package com.example.newappcompose.activities.splash

// Importa classes necessárias para a Activity e Composable
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newappcompose.MainActivity
import com.example.newappcompose.R
import com.example.newappcompose.activities.dashboards.DashboardActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// Classe SplashActivity que herda de ComponentActivity
class SplashActivity : ComponentActivity() {
    // Método chamado ao criar a activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita o conteúdo ocupar toda a tela, incluindo as bordas
        enableEdgeToEdge()
        // Define o conteúdo da tela com Composable
        setContent {
            // Exibe a tela de splash e define a ação ao clicar em "Começar"
            SplashScreen(onGetStartedClick = {
                // Inicia a DashboardActivity ao clicar no botão
                startActivity(Intent(this, DashboardActivity::class.java))
            })
        }
    }
}

// Composable que representa a tela de splash
@Composable
@Preview // Permite visualizar no Android Studio
fun SplashScreen(onGetStartedClick: () -> Unit = {}) {
    // Altera a cor da status bar
    StatusTopBarColor()

    // Container principal da tela
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagem de fundo cobrindo toda a tela
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null, // Descrição nula porque é decorativa
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Recorta para preencher a área
        )

        // Coluna contendo os textos e o botão
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 80.dp),
            verticalArrangement = Arrangement.Bottom, // Alinha ao final da tela
            horizontalAlignment = Alignment.CenterHorizontally // Centraliza horizontalmente
        ) {
            // Título principal da tela
            Text(
                text = "Eleve Suas Jornadas, Voe Sem Problemas.",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Texto secundário de explicação
            Text(
                text = "Descubra reservas rápidas de voos com tarifas incomparáveis",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.9f), // Um pouco transparente
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botão para iniciar o app
            Button(
                onClick = onGetStartedClick, // Executa ação ao clicar
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White // Cor de fundo do botão
                ),
                shape = RoundedCornerShape(size = 28.dp) // Arredondamento do botão
            ) {
                // Texto dentro do botão
                Text(
                    text = "COMEÇAR",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    letterSpacing = 1.sp // Espaçamento entre letras
                )
            }
        }
    }
}

// Composable que define a cor da status bar
@Composable
fun StatusTopBarColor() {
    val systemUiController = rememberSystemUiController()
    // Executa efeito colateral para aplicar a cor
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent, // Define a status bar como transparente
            darkIcons = false // Ícones claros para fundo escuro
        )
    }
}
