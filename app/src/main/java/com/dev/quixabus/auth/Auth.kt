package com.dev.quixabus.auth

import com.dev.quixabus.dao.UsuarioDao
import com.dev.quixabus.model.Usuario

class Auth {

    private val usuarioDao = UsuarioDao()
    val usuarioLogado: Usuario = usuarioDao.buscaPorId(1)

}