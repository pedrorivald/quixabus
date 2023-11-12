package com.dev.quixabus.dao

import com.dev.quixabus.model.Horario
import com.dev.quixabus.model.Parada
import com.dev.quixabus.model.TipoParada

class ItinerarioDao {

    fun buscaParadas(): List<Parada> {
        return paradas.toList()
    }

    fun buscaHorarios(): List<Horario> {
        return horarios.toList()
    }

    companion object {
        private val paradas = mutableListOf<Parada>(
            Parada(
                id = 1,
                endereco = "R. Basílio Emiliano Pinto, frente ao Seminário, s/n – Combate",
                tipo = TipoParada.DESEMBARQUE,
                latitude = -4.970699485516595,
                longitude = -39.02542071286042
            ),
            Parada(
                id = 2,
                endereco = "Av. José Caetano Almeida, 861 – Combate",
                tipo = TipoParada.DESEMBARQUE,
                latitude = -4.972641366825846,
                longitude =  -39.0224537772988
            ),
            Parada(
                id = 3,
                endereco = "Av. José Caetano Almeida, 503 - Centro",
                tipo = TipoParada.DESEMBARQUE,
                latitude = -4.972629867657732,
                longitude = -39.019771979291384
            ),
            Parada(
                id = 4,
                endereco = "R. Basílio Emiliano Pinto, 420 - Centro",
                tipo = TipoParada.EMBARQUE,
                latitude = -4.970118968668568,
                longitude = -39.01937309998612
            ),
            Parada(
                id = 5,
                endereco = "R. Basílio Emiliano Pinto, 958 – Planalto Universitário",
                tipo = TipoParada.EMBARQUE,
                latitude = -4.970113406942949,
                longitude =  -39.02423824935796
            ),
            Parada(
                id = 6,
                endereco = "R. Basílio Emiliano Pinto, 1290 - Combate",
                tipo = TipoParada.EMBARQUE,
                latitude = -4.971280642598359,
                longitude = -39.02715588806771
            ),
            Parada(
                id = 7,
                endereco = "2406, R. Pres. Vargas, 2320 - Centro",
                tipo = TipoParada.RODOVIARIA,
                latitude = -4.972965547583367,
                longitude = -39.01634978245583
            ),
            Parada(
                id = 8,
                endereco = "Av. José de Freitas Queiroz, 5003",
                tipo = TipoParada.CAMPUS,
                latitude = -4.979480291253761,
                longitude = -39.056233828167805
            )
        )

        private val horarios = mutableListOf<Horario>(
            Horario(
                id = 1,
                horario = "07:10 até 07:50",
                quantidadeViagens = "várias",
                responsavelOnibus = "PMQ",
                embarque = true,
                desembarque = false
            ),
            Horario(
                id = 2,
                horario = "09:15",
                quantidadeViagens = "2",
                responsavelOnibus = "UFC",
                embarque = true,
                desembarque = true
            ),
            Horario(
                id = 3,
                horario = "11:10 até 13:30",
                quantidadeViagens = "várias",
                responsavelOnibus = "PMQ",
                embarque = true,
                desembarque = true
            ),
            Horario(
                id = 4,
                horario = "15:15",
                quantidadeViagens = "2",
                responsavelOnibus = "UFC",
                embarque = true,
                desembarque = true
            ),
            Horario(
                id = 5,
                horario = "17:00 até 18:30",
                quantidadeViagens = "várias",
                responsavelOnibus = "PMQ",
                embarque = true,
                desembarque = true
            ),
            Horario(
                id = 6,
                horario = "21:50 até 22:10",
                quantidadeViagens = "várias",
                responsavelOnibus = "PMQ",
                embarque = false,
                desembarque = true
            )
        )
    }
}