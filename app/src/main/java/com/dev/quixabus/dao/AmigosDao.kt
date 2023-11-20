package com.dev.quixabus.dao

import com.dev.quixabus.model.Amigo
import com.dev.quixabus.model.AmigoItem
import com.dev.quixabus.model.Usuario

class AmigosDao {

    private val usuarioDao = UsuarioDao()

    fun adicionar(amigo: Amigo) {
        amigos.add(amigo)
    }

    fun deletarPorIdUsuarioSolicitado(idUsuarioSolicitado: Int) {
        val index = amigos.indexOfFirst { it.idUsuarioSolicitado == idUsuarioSolicitado }
        amigos.removeAt(index)
    }

    fun buscaPorIdUsuarioSolicitante(idUsuarioSolicitante: Int): List<Amigo> {
        return amigos.filter { it.idUsuarioSolicitante == idUsuarioSolicitante }
    }

    fun buscaAmigosItems(idUsuario: Int): List<AmigoItem> {
        val usuario = usuarioDao.buscaPorId(idUsuario)

        val amigos = buscaPorIdUsuarioSolicitante(idUsuario)
        val amigosItems = mutableListOf<AmigoItem>()

        amigos.forEach {
            val usuarioSolicitado = usuarioDao.buscaPorId(it.idUsuarioSolicitado)
            amigosItems.add(AmigoItem(usuarioSolicitante = usuario, usuarioSolicitado = usuarioSolicitado))
        }

        return amigosItems.toList()
    }

    fun buscaAmigos(id: Int): List<Usuario> {
        val amigos: List<Amigo> = buscaPorIdUsuarioSolicitante(id)
        val list = mutableListOf<Usuario>()

        amigos.forEach {
            list.add(usuarioDao.buscaPorId(it.idUsuarioSolicitado))
        }

        return list.toList()
    }

    companion object {
        private val amigos = mutableListOf<Amigo>(
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 2
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 3
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 1,
                idUsuarioSolicitado = 4
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 3,
                idUsuarioSolicitado = 2
            ),
            Amigo(
                id = 1,
                idUsuarioSolicitante = 3,
                idUsuarioSolicitado = 4
            )
        )
    }
}