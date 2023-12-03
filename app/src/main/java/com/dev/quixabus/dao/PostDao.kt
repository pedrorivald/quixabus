package com.dev.quixabus.dao

import com.dev.quixabus.model.Post
import com.dev.quixabus.model.PostOld
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostDao {

    val comentarioDao = ComentarioDao()

    fun adicionar(post: PostOld) {
        posts.add(post)
    }

    fun salvar(post: Post, callback: (Boolean) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("posts")
            .child(FirebaseHelper.getIdUser()?: "")
            .child(post.id)
            .setValue(post)
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

    fun buscarTodos(usuarioId: String, callback: (List<Post>?) -> Unit) {
        val postList = mutableListOf<Post>()

        FirebaseHelper.getDatabase()
            .child("posts")
            .child(usuarioId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val post = snap.getValue(Post::class.java) as Post
                            postList.add(post)
                        }

                        callback(postList)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun buscarPorId(id: String, usuarioId: String, callback: (Post?) -> Unit) {
        FirebaseHelper.getDatabase()
            .child("posts")
            .child(usuarioId)
            .child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val post = snapshot.children.first().getValue(Post::class.java) as Post
                        callback(post)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun atualizar(post: PostOld) {
        val index = getIndex(post.id)
        posts.set(index, post)
    }

    fun deletar(id: Int) {
        //deletar comentarios do post
        val comentarios = comentarioDao.buscaPorIdPost(id)
        comentarios.forEach {
            comentarioDao.deletar(it.id)
        }

        val index = getIndex(id)
        posts.removeAt(index)
    }

    fun buscaTodos() : List<PostOld> {
        return posts.toList()
    }

    fun buscaPorIdUsuario(idUsuario: Int): List<PostOld> {
        return posts.filter { it.idUsuario == idUsuario }
    }

    private fun getIndex(id: Int): Int {
        return posts.indexOfFirst { it.id == id }
    }

    companion object {
        private val posts = mutableListOf<PostOld>(
            PostOld(
                id = 1,
                texto = "Post 1",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            PostOld(
                id = 2,
                texto = "Post 2",
                idUsuario = 3,
                data = "10/11/2023"
            ),
            PostOld(
                id = 3,
                texto = "Post 3",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            PostOld(
                id = 4,
                texto = "Post 4",
                idUsuario = 2,
                data = "10/11/2023"
            ),
            PostOld(
                id = 5,
                texto = "Post 5",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            PostOld(
                id = 6,
                texto = "Post 6",
                idUsuario = 4,
                data = "10/11/2023"
            )
        )
    }
}