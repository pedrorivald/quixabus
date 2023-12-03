package com.dev.quixabus.dao

import com.dev.quixabus.model.Comentario
import com.dev.quixabus.model.ComentarioOld
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ComentarioDao {

    fun adicionar(comentario: ComentarioOld) {
        comentarios.add(comentario)
    }

    fun salvar(feedItem: FeedItem, comentario: Comentario, callback: (Boolean) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("posts")
            .child(feedItem.usuario.id)
            .child(feedItem.post.id)
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

    fun deletar(id: Int) {
        val index = comentarios.indexOfFirst { it.id == id }
        comentarios.removeAt(index)
    }

    fun buscaPorIdPost(idPost: Int): List<ComentarioOld> {
        return comentarios.filter { it.idPost == idPost}
    }

    companion object {
        private val comentarios = mutableListOf<ComentarioOld>(
            ComentarioOld(
                id = 1,
                idUsuario = 1,
                idPost = 1,
                texto = "Comentario 1",
                data = "10/11/2023"
            ),
            ComentarioOld(
                id = 2,
                idUsuario = 1,
                idPost = 1,
                texto = "Comentario 2",
                data = "10/11/2023"
            ),
            ComentarioOld(
                id = 3,
                idUsuario = 1,
                idPost = 2,
                texto = "Comentario 3",
                data = "10/11/2023"
            ),
            ComentarioOld(
                id = 4,
                idUsuario = 2,
                idPost = 1,
                texto = "Comentario 4",
                data = "10/11/2023"
            ),
            ComentarioOld(
                id = 5,
                idUsuario = 3,
                idPost = 2,
                texto = "Comentario 5",
                data = "10/11/2023"
            ),
            ComentarioOld(
                id = 6,
                idUsuario = 3,
                idPost = 1,
                texto = "Comentario 6",
                data = "10/11/2023"
            )
        )
    }
}  