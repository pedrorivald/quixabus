package com.dev.quixabus.dao

import com.dev.quixabus.model.Amigo
import com.dev.quixabus.model.AmigoItem
import com.dev.quixabus.model.Usuario
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AmigosDao {

    private val usuarioDao = UsuarioDao()

    fun salvar(email: String, callback: (Boolean) -> Unit) {
        usuarioDao.getUsuarioPorEmail(email) { usuario ->
            if(usuario != null) {
                FirebaseHelper.getDatabase()
                    .child("usuarios")
                    .child(FirebaseHelper.getIdUser()?: "")
                    .child("amigos")
                    .child(usuario.id)
                    .setValue(true)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            callback(true)
                        } else {
                            callback(false)
                        }
                    }.addOnFailureListener {
                        callback(false)
                    }
            } else {
                callback(false)
            }
        }
    }

    fun buscaAmigos(callback: (List<Usuario>?) -> Unit) {
        val amigoList = mutableListOf<Usuario>()

        FirebaseHelper.getDatabase()
            .child("usuarios")
            .child(FirebaseHelper.getIdUser()?: "")
            .child("amigos")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val iterator = snapshot.children.iterator()
                        while (iterator.hasNext()) {
                            val snap = iterator.next()
                            val amigoId = snap.key

                            if(amigoId != null) {

                                if (!iterator.hasNext()) {
                                    usuarioDao.getUsuarioPorId(amigoId) { usuario ->
                                        if (usuario != null) {
                                            amigoList.add(usuario)
                                            callback(amigoList)
                                        }
                                    }
                                } else {
                                    usuarioDao.getUsuarioPorId(amigoId) { usuario ->
                                        if (usuario != null) {
                                            amigoList.add(usuario)
                                        }
                                    }
                                }
                            }

                        }
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun deletar(id: String, callback: (Boolean) -> Unit) {
        val amigoRef = FirebaseHelper.getDatabase()
            .child("usuarios")
            .child(FirebaseHelper.getIdUser()?:"")
            .child("amigos")
            .child(id)

        amigoRef.removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun buscaPorIdUsuarioSolicitante(idUsuarioSolicitante: Int): List<Amigo> {
        return amigos.filter { it.idUsuarioSolicitante == idUsuarioSolicitante }
    }

    fun buscaAmigosItems(idUsuario: Int): List<AmigoItem> {
        val amigosItems = mutableListOf<AmigoItem>()
        return amigosItems
//        val usuario = usuarioDao.getUsuarioPorId(idUsuario)
//
//        val amigos = buscaPorIdUsuarioSolicitante(idUsuario)
//        val amigosItems = mutableListOf<AmigoItem>()
//
//        amigos.forEach {
//            val usuarioSolicitado = usuarioDao.getUsuarioPorId(it.idUsuarioSolicitado)
//            amigosItems.add(AmigoItem(usuarioSolicitante = usuario, usuarioSolicitado = usuarioSolicitado))
//        }
//
//        return amigosItems.toList()
    }

    companion object {
        private val amigos = mutableListOf<Amigo>(
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 2
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 3
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 4
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 3,
                idUsuarioSolicitado = 2
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 3,
                idUsuarioSolicitado = 4
            )
        )
    }
}