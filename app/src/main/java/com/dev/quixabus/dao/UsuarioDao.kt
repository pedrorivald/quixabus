package com.dev.quixabus.dao

import com.dev.quixabus.model.Usuario
import com.dev.quixabus.model.UsuarioOld
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UsuarioDao {

    fun getUsuarioPorId(id: String, callback: (Usuario?) -> Unit) {

        FirebaseHelper.getDatabase()
            .child("usuarios")
            .child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val usuario = snapshot.getValue(Usuario::class.java) as Usuario
                        callback(usuario)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun getUsuarioPorEmail(email: String, callback: (Usuario?) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("usuarios")
            .orderByChild("email")
            .equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val usuario = snapshot.children.first()
                        .getValue(Usuario::class.java) as Usuario

                    callback(usuario)
                } else {
                    callback(null) // Nenhum usuário encontrado com o email fornecido
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }

    fun buscaPorEmail(email: String): UsuarioOld {
        return usuarios.filter { it.email == email }[0]
    }

    companion object {
        private val usuarios = mutableListOf<UsuarioOld>(
            UsuarioOld(
                id = 1,
                nome = "Pedro",
                email = "pedro@gmail.com"
            ),
            UsuarioOld(
                id = 2,
                nome = "Tiago",
                email = "tiago@gmail.com"
            ),
            UsuarioOld(
                id = 3,
                nome = "João",
                email = "joao@gmail.com"
            ),
            UsuarioOld(
                id = 4,
                nome = "Marcos",
                email = "marcos@gmail.com"
            ),
            UsuarioOld(
                id = 5,
                nome = "Lucas",
                email = "lucas@gmail.com"
            ),
        )
    }

}