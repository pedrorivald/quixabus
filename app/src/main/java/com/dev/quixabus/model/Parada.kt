package com.dev.quixabus.model

enum class TipoParada {
    DESEMBARQUE, EMBARQUE, RODOVIARIA, CAMPUS
}
data class Parada (
    val id: Int,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val tipo: TipoParada
)