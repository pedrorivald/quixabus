package com.dev.quixabus.model
data class Comentario (
    val id: Int,
    val idUsuario: Int,
    val idPost: Int,
    val texto: String,
    val data: String
)