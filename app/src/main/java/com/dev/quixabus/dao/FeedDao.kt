package com.dev.quixabus.dao

import com.dev.quixabus.model.ComentarioItem
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.model.Post

class FeedDao {

    val postDao = PostDao()
    val usuarioDao = UsuarioDao()
    val comentarioDao = ComentarioDao()

    //posts dos amigos e do proprio usuario
    fun buscaFeed(idUsuario: Int): List<FeedItem> {
        val amigos = usuarioDao.buscaAmigos(idUsuario)
        val posts: List<Post> = postDao.buscaTodos()

        val feed = mutableListOf<FeedItem>()

        posts.forEach {
            val usuario = usuarioDao.buscaPorId(it.idUsuario)

            if (usuario.id == idUsuario || amigos.contains(usuario)) {
                feed.add(FeedItem(post = it, usuario = usuario))
            }
        }

        return feed.toList()
    }

    fun buscaComentariosPorPost(idPost: Int): List<ComentarioItem> {
        val comentarios = comentarioDao.buscaPorIdPost(idPost)
        val comentariosItems = mutableListOf<ComentarioItem>()

        comentarios.forEach {
            val usuario = usuarioDao.buscaPorId(it.idUsuario)
            comentariosItems.add(ComentarioItem(comentario = it, usuario = usuario))
        }

        return comentariosItems.toList()
    }

}