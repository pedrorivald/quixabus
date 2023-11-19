package com.dev.quixabus.dao

import com.dev.quixabus.model.Post

class PostDao {
    fun adicionar(post: Post) {
        posts.add(post)
    }

    fun atualizar(post: Post) {
        val index = getIndex(post.id)
        posts.set(index, post)
    }

    fun deletar(id: Int) {
        val index = getIndex(id)
        posts.removeAt(index)
    }

    fun buscaTodos() : List<Post> {
        return posts.toList()
    }

    fun buscaPorIdUsuario(idUsuario: Int): List<Post> {
        return posts.filter { it.idUsuario == idUsuario }
    }

    fun buscaPorId(id: Int): Post {
        return posts.filter { it.id == id }[0]
    }

    private fun getIndex(id: Int): Int {
        return posts.indexOfFirst { it.id == id }
    }

    companion object {
        private val posts = mutableListOf<Post>(
            Post(
                id = 1,
                texto = "Post 1",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            Post(
                id = 2,
                texto = "Post 2",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            Post(
                id = 3,
                texto = "Post 3",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            Post(
                id = 4,
                texto = "Post 4",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            Post(
                id = 5,
                texto = "Post 5",
                idUsuario = 1,
                data = "10/11/2023"
            ),
            Post(
                id = 6,
                texto = "Post 6",
                idUsuario = 1,
                data = "10/11/2023"
            )
        )
    }
}