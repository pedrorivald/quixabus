package com.dev.quixabus.dao

import com.dev.quixabus.model.Aula
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AulasDao {

    fun salvar(aula: Aula, callback: (Boolean) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("aulas")
            .child(FirebaseHelper.getIdUser()?: "")
            .child(aula.id)
            .setValue(aula)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }.addOnFailureListener {
                callback(false)
            }
    }

    fun buscarPorDia(usuarioId: String, diaDaSemana: String, callback: (List<Aula>?) -> Unit) {
        val aulaList = mutableListOf<Aula>()

        FirebaseHelper.getDatabase()
            .child("aulas")
            .child(usuarioId)
            .orderByChild("diaSemana")
            .equalTo(diaDaSemana)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val aula = snap.getValue(Aula::class.java) as Aula
                            aulaList.add(aula)
                        }

                        callback(aulaList)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun atualizar(aulaId: String,
                  usuarioId: String,
                  diaSemana: String,
                  nome: String,
                  professor: String,
                  bloco: String,
                  sala: String,
                  turma: String,
                  horarioInicio: String,
                  horarioFim: String,
                  callback: (Boolean) -> Unit) {

        val aulaRef = FirebaseHelper.getDatabase()
            .child("aulas")
            .child(usuarioId)
            .child(aulaId)

        val novo = hashMapOf<String, Any>(
            "aulaId" to aulaId,
            "diaSemana" to diaSemana,
            "nome" to nome,
            "professor" to professor,
            "bloco" to bloco,
            "sala" to sala,
            "turma" to turma,
            "horarioInicio" to horarioInicio,
            "horarioFim" to horarioFim
        )

        aulaRef.updateChildren(novo)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun deletar(aulaId: String, callback: (Boolean) -> Unit) {
        val aulaRef = FirebaseHelper.getDatabase()
            .child("aulas")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(aulaId)

        aulaRef.removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun buscarPorId(aulaId: String, usuarioId: String, callback: (Aula?) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("aulas")
            .child(usuarioId)
            .child(aulaId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val aula = snapshot.getValue(Aula::class.java) as Aula
                        callback(aula)
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