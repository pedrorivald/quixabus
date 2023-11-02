package com.dev.quixabus.dao

import com.dev.quixabus.model.Aula
import com.dev.quixabus.model.DiaSemana

class AulasDao {

    fun adicionar(aula: Aula) {
        aulas.add(aula)
    }

    fun atualizar(aula: Aula) {
        val index = getIndex(aula.id)
        aulas.set(index, aula)
    }

    fun deletar(id: Int) {
        val index = getIndex(id)
        aulas.removeAt(index)
    }

    fun buscaTodos() : List<Aula> {
        return aulas.toList()
    }

    fun buscaPorDia(dia: DiaSemana): List<Aula> {
        return aulas.filter { it.diaSemana == dia }
    }

    fun buscaPorId(id: Int): Aula {
        return aulas.filter { it.id == id }[0]
    }

    private fun getIndex(id: Int): Int {
        return aulas.indexOfFirst { it.id == id }
    }

    companion object {
        private val aulas = mutableListOf<Aula>(
            Aula(
                id = 1,
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
                id = 2,
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
                id = 3,
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
                id = 4,
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
                id = 5,
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
                id = 6,
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