package com.example.newappcompose.domain

import java.io.Serializable


data class FlightModel( // Modelo de dados para representar um voo
    var AirlineLogo: String = "",      // URL do logo da companhia aérea
    var AirlineName: String = "",      // Nome da companhia aérea
    var ArriveTime: String = "",       // Horário de chegada
    var ClassSeat: String = "",        // Classe do assento
    var Date: String = "",             // Data do voo
    var From: String = "",             // Nome completo da origem
    var FromShort: String = "",        // Código abreviado da origem
    var NumberSeat: Int = 0,           // Número de assentos disponíveis
    var Price: Double = 0.0,           // Preço base do voo
    var Passenger: String = "",        // Lista de passageiros/assentos selecionados
    var Seats: String = "",            // Informações sobre assentos
    var ReservedSeats: String = "",    // Assentos já reservados
    var Time: String = "",             // Horário do voo
    var To: String = "",               // Nome completo do destino
    var ToShort: String = ""           // Código abreviado do destino
) : Serializable // Permite serialização para passar entre Activities