package com.dev.quixabus.dao

import com.dev.quixabus.model.Amigo
import com.dev.quixabus.model.Usuario

class UsuarioDao {

    val amigosDao = AmigosDao()

    fun buscaPorId(id: Int): Usuario {
        return usuarios.filter { it.id == id }[0]
    }

    fun buscaAmigos(id: Int): List<Usuario> {
        val amigos: List<Amigo> = amigosDao.buscaPorIdUsuarioSolicitante(id)
        val list = mutableListOf<Usuario>()

        amigos.forEach {
            list.add(buscaPorId(it.idUsuarioSolicitado))
        }

        return list.toList()
    }

    companion object {
        private val usuarios = mutableListOf<Usuario>(
            Usuario(
                id = 1,
                nome = "Pedro",
                email = "pedro@gmail.com"
            ),
            Usuario(
                id = 2,
                nome = "Tiago",
                email = "pedro@gmail.com"
            ),
            Usuario(
                id = 3,
                nome = "João",
                email = "pedro@gmail.com"
            ),
            Usuario(
                id = 4,
                nome = "Marcos",
                email = "pedro@gmail.com"
            ),
        )
    }

}