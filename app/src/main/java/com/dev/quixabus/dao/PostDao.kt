package com.dev.quixabus.dao

import com.dev.quixabus.model.Post
import com.dev.quixabus.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostDao {

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
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val post = snapshot.getValue(Post::class.java) as Post
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

    fun atualizar(postId: String, texto: String, usuarioId: String, callback: (Boolean) -> Unit) {

        val postRef = FirebaseHelper.getDatabase()
            .child("posts")
            .child(usuarioId)
            .child(postId)

        val novo = hashMapOf<String, Any>(
            "texto" to texto
        )

        postRef.updateChildren(novo)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun deletar(id: String, callback: (Boolean) -> Unit) {
        val postRef = FirebaseHelper.getDatabase()
            .child("posts")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(id)

        postRef.removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}