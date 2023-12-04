package com.dev.quixabus.dao

import com.dev.quixabus.model.ComentarioItem
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.util.FirebaseHelper

class FeedDao {

    val postDao = PostDao()
    val usuarioDao = UsuarioDao()
    val amigosDao = AmigosDao()
    val comentarioDao = ComentarioDao()

    //posts dos amigos e do proprio usuario
    fun buscaFeed(callback: (List<FeedItem>?) -> Unit) {
        val feedList = mutableListOf<FeedItem>()

        //posts dos amigos
        amigosDao.buscaAmigos { amigos ->
            if (amigos != null) {

                val iterator = amigos.iterator()

                while (iterator.hasNext()) {
                    val amigo = iterator.next()
                    postDao.buscarTodos(amigo.id) { posts ->
                        if(posts != null) {
                            posts.forEach { post ->
                                feedList.add(FeedItem(post = post, usuario = amigo))
                                callback(feedList)
                            }
                        } else {
                            callback(null)
                        }
                    }
                }
            } else {
                callback(null)
            }
        }

        //posts do usuario
        usuarioDao.getUsuarioPorId(FirebaseHelper.getIdUser()?: "") { usuario ->
            if(usuario != null) {
                postDao.buscarTodos(usuario.id) { postsUser ->
                    postsUser?.forEach { postUser ->
                        feedList.add(FeedItem(post = postUser, usuario = usuario))
                        callback(feedList)
                    }
                }
            }
        }
    }


    fun buscaComentariosPorPost(postId: String, usuarioId: String, callback: (List<ComentarioItem>?) -> Unit) {
        val comentarioList = mutableListOf<ComentarioItem>()

        comentarioDao.buscarComentariosPorIdPost(postId, usuarioId) { comentarios ->
            if(comentarios != null) {
                comentarios.forEach { comentario ->
                    usuarioDao.getUsuarioPorId(comentario.usuarioId) { usuario ->
                        if (usuario != null) {
                            comentarioList.add(ComentarioItem(comentario = comentario, usuario = usuario))
                            callback(comentarioList)
                        }
                    }
                }
            } else {
                callback(null)
            }
        }
    }

}