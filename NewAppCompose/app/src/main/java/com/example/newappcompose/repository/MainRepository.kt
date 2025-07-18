package com.example.newappcompose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newappcompose.domain.FlightModel
import com.example.newappcompose.domain.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainRepository { // Repositório para acessar dados do Firebase

    private val firebaseDatabase = FirebaseDatabase.getInstance() // Instância do Firebase Database

    fun loadLocation(): LiveData<MutableList<LocationModel>> { // Carrega lista de localizações
        val listData = MutableLiveData<MutableList<LocationModel>>() // LiveData para observar mudanças
        val ref = firebaseDatabase.getReference("Locations") // Referência para nó "Locations"

        ref.addValueEventListener(object : ValueEventListener { // Listener para mudanças
            override fun onDataChange(snapshot: DataSnapshot) { // Quando dados mudam
                val list = mutableListOf<LocationModel>() // Lista temporária

                for (childSnapshot in snapshot.children) { // Itera sobre filhos
                    val item = childSnapshot.getValue(LocationModel::class.java) // Converte para modelo
                    item?.let { list.add(it) } // Adiciona à lista se não for nulo
                }

                listData.value = list // Atualiza LiveData
            }

            override fun onCancelled(error: DatabaseError) { // Em caso de erro
                TODO("Not yet implemented") // Implementação pendente
            }
        })

        return listData // Retorna LiveData
    }

    fun loadFiltered(from: String, to: String): LiveData<MutableList<FlightModel>> { // Carrega voos filtrados
        val listData = MutableLiveData<MutableList<FlightModel>>() // LiveData para voos
        val ref = firebaseDatabase.getReference("Flights") // Referência para nó "Flights"

        val query: Query = ref.orderByChild("from").equalTo(from) // Query filtrada por origem

        query.addListenerForSingleValueEvent(object : ValueEventListener { // Listener único
            override fun onDataChange(snapshot: DataSnapshot) { // Quando dados chegam
                val lists = mutableListOf<FlightModel>() // Lista temporária

                for (childSnapshot in snapshot.children) { // Itera sobre filhos
                    val list = childSnapshot.getValue(FlightModel::class.java) // Converte para modelo
                    if (list != null) { // Se não é nulo
                        if (list.To == to) { // E destino confere
                            lists.add(list) // Adiciona à lista
                        }
                    }
                }

                listData.value = lists // Atualiza LiveData
            }

            override fun onCancelled(error: DatabaseError) { // Em caso de erro
                TODO("Not yet implemented") // Implementação pendente
            }
        })

        return listData
    }
}
