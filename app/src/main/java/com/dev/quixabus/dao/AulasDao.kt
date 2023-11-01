package com.dev.quixabus.dao

import com.dev.quixabus.model.Aula
import com.dev.quixabus.model.DiaSemana

class AulasDao {

    fun adiciona(aula: Aula) {
        aulas.add(aula)
    }

    fun buscaTodos() : List<Aula> {
        return aulas.toList()
    }

    fun buscaPorDia(dia: DiaSemana): List<Aula> {
        return aulas.filter { it.diaSemana == dia }
    }

    companion object {
        private val aulas = mutableListOf<Aula>(
            Aula(
                nome = "Programação WEB",
                diaSemana = DiaSemana.SEGUNDA,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            ),
            Aula(
                nome = "Programação WEB 2",
                diaSemana = DiaSemana.SEGUNDA,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            ),
            Aula(
                nome = "Linguagens de Marcação e Scripts",
                diaSemana = DiaSemana.TERCA,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            ),
            Aula(
                nome = "Programação WEB",
                diaSemana = DiaSemana.TERCA,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            ),
            Aula(
                nome = "Programação WEB 2",
                diaSemana = DiaSemana.SABADO,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            ),
            Aula(
                nome = "Linguagens de Marcação e Scripts",
                diaSemana = DiaSemana.SEXTA,
                bloco = "3",
                sala = "4",
                professor = "João",
                turma = "2",
                horarioInicio = "08:00",
                horarioFim = "10:00"
            )
        )
    }

}