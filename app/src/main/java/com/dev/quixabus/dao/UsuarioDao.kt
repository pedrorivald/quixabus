package com.dev.quixabus.dao

import com.dev.quixabus.model.Usuario

class UsuarioDao {

    fun buscaPorId(id: Int): Usuario {
        return usuarios.filter { it.id == id }[0]
    }

    fun buscaPorEmail(email: String): Usuario {
        return usuarios.filter { it.email == email }[0]
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
                email = "tiago@gmail.com"
            ),
            Usuario(
                id = 3,
                nome = "João",
                email = "joao@gmail.com"
            ),
            Usuario(
                id = 4,
                nome = "Marcos",
                email = "marcos@gmail.com"
            ),
            Usuario(
                id = 5,
                nome = "Lucas",
                email = "lucas@gmail.com"
            ),
        )
    }

}