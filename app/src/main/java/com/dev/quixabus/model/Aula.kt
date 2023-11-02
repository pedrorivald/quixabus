package com.dev.quixabus.model

enum class DiaSemana {
    DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO
}

data class Aula (
    val id: Int,
    val diaSemana: DiaSemana,
    val nome: String,
    val professor: String,
    val bloco: String,
    val sala: String,
    val turma: String,
    val horarioInicio: String,
    val horarioFim: String
)