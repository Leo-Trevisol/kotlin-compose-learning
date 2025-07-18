package com.example.newappcompose.viewmodel

import androidx.lifecycle.LiveData
import com.example.newappcompose.domain.FlightModel
import com.example.newappcompose.domain.LocationModel
import com.example.newappcompose.repository.MainRepository

class MainViewModel { // ViewModel para gerenciar dados da UI

    private val repository = MainRepository() // Instância do repositório

    fun loadLocations(): LiveData<MutableList<LocationModel>> { // Carrega localizações
        return repository.loadLocation() // Delega para repositório
    }

    fun loadFiltered(from: String, to: String): LiveData<MutableList<FlightModel>> { // Carrega voos filtrados
        return repository.loadFiltered(from, to) // Delega para repositório
    }
}
