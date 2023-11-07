package com.dev.quixabus.model

data class Horario (
    val id: Int,
    val horario: String,
    val quantidadeViagens: String,
    val responsavelOnibus: String,
    val campusParaRodoviaria: Boolean,
    val rodoviariaParaCampus: Boolean
)