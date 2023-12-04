package com.dev.quixabus.dao

import com.dev.quixabus.model.Comentario
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ComentarioDao {

    fun salvar(postId: String, usuarioId: String, comentario: Comentario, callback: (Boolean) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("posts")
            .child(usuarioId)
            .child(postId)
            .child("comentarios")
            .child(comentario.id)
            .setValue(comentario)
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

    fun buscarComentariosPorIdPost(id: String, usuarioId: String, callback: (List<Comentario>?) -> Unit) {
        val comentarioList = mutableListOf<Comentario>()

        FirebaseHelper.getDatabase()
            .child("posts")
            .child(usuarioId)
            .child(id)
            .child("comentarios")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val comentario = snap.getValue(Comentario::class.java) as Comentario
                            comentarioList.add(comentario)
                        }

                        callback(comentarioList)
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