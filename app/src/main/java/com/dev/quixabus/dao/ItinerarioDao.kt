package com.dev.quixabus.dao

import com.dev.quixabus.model.Horario
import com.dev.quixabus.model.Parada
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ItinerarioDao {

    fun buscarParadas(callback: (List<Parada>?) -> Unit) {
        val paradaList = mutableListOf<Parada>()

        FirebaseHelper.getDatabase()
            .child("paradas")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val parada = snap.getValue(Parada::class.java) as Parada
                            paradaList.add(parada)
                        }

                        callback(paradaList)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun buscarHorarios(callback: (List<Horario>?) -> Unit) {
        val horarioList = mutableListOf<Horario>()

        FirebaseHelper.getDatabase()
            .child("horarios")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val horario = snap.getValue(Horario::class.java) as Horario
                            horarioList.add(horario)
                        }

                        callback(horarioList)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }
}