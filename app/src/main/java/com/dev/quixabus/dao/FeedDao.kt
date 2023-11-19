package com.dev.quixabus.dao

import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.model.Post

class FeedDao {

    val postDao = PostDao()
    val usuarioDao = UsuarioDao()

    fun buscaFeed(): List<FeedItem> {
        val posts: List<Post> = postDao.buscaTodos()
        val list = mutableListOf<FeedItem>()

        posts.forEach {
            val usuario = usuarioDao.buscaPorId(it.idUsuario)
            list.add(FeedItem(post = it, usuario = usuario))
        }

        return list.toList()
    }

}