package com.example.kotlincomposelearning

import ListTask
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlincomposelearning.view.SaveTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ativa o modo "Edge-to-Edge", permitindo que o conteúdo do app ocupe toda a tela,
        // inclusive atrás da barra de status e da navegação, deixando o app com visual moderno.
        enableEdgeToEdge()

        // Bloco onde se define toda a interface do app usando apenas Compose (Kotlin puro, sem XML).
        setContent {

            // Cria o NavController, responsável por controlar a troca de telas dentro do Compose.
            // rememberNavController mantém o mesmo controlador em memória durante recomposições da UI,
            // evitando que ele seja recriado e perca o estado atual da navegação.
            val navController = rememberNavController()

            // NavHost é o container que gerencia quais telas (rotas) estão disponíveis no app.
            // O navController é passado para gerenciar a navegação.
            // startDestination define que a primeira tela a ser mostrada será "listTasks".
            NavHost(
                navController = navController,
                startDestination = "listTasks"
            ) {
                // Define a rota "listTasks".
                // Quando o app estiver nesta rota, o Composable ListTask será exibido.
                // O navController é passado para dentro da tela, permitindo que ela mesma controle a navegação.
                composable("listTasks") {
                    ListTask(navController)
                }

                // Define a rota "saveTasks".
                // Quando o usuário navegar para essa rota, o Composable SaveTask será exibido.
                // Novamente, o navController é passado, possibilitando navegação de volta ou para outras rotas.
                composable("saveTasks") {
                    SaveTask(navController)
                }

            }
        }
    }
}

