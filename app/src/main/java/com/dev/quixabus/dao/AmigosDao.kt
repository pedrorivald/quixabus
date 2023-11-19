package com.dev.quixabus.dao

import com.dev.quixabus.model.Amigo

class AmigosDao {

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