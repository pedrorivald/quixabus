package com.dev.quixabus.dao

import com.dev.quixabus.model.Comentario

class ComentarioDao {

    fun adicionar(comentario: Comentario) {
        comentarios.add(comentario)
    }

    fun deletar(id: Int) {
        val index = comentarios.indexOfFirst { it.id == id }
        comentarios.removeAt(index)
    }

    fun buscaPorIdPost(idPost: Int): List<Comentario> {
        return comentarios.filter { it.idPost == idPost}
    }

    companion object {
        private val comentarios = mutableListOf<Comentario>(
            Comentario(
                id = 1,
                idUsuario = 1,
                idPost = 1,
                texto = "Comentario 1",
                data = "10/11/2023"
            ),
            Comentario(
                id = 2,
                idUsuario = 1,
                idPost = 1,
                texto = "Comentario 2",
                data = "10/11/2023"
            ),
            Comentario(
                id = 3,
                idUsuario = 1,
                idPost = 2,
                texto = "Comentario 3",
                data = "10/11/2023"
            ),
            Comentario(
                id = 4,
                idUsuario = 2,
                idPost = 1,
                texto = "Comentario 4",
                data = "10/11/2023"
            ),
            Comentario(
                id = 5,
                idUsuario = 3,
                idPost = 2,
                texto = "Comentario 5",
                data = "10/11/2023"
            ),
            Comentario(
                id = 6,
                idUsuario = 3,
                idPost = 1,
                texto = "Comentario 6",
                data = "10/11/2023"
            )
        )
    }
}  