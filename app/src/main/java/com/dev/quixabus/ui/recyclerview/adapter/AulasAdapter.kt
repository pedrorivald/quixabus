package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityAulaBinding
import com.dev.quixabus.model.Aula

class AulasAdapter(
    private val context: Context,
    aulas: List<Aula>,
    var clickAula: ClickAula
): RecyclerView.Adapter<AulasAdapter.ViewHolder>() {

    private val aulas = aulas.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ActivityAulaBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = aulas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aula = aulas[position]
        holder.vincula(aula)

        holder.cardView.setOnClickListener {
            clickAula.clickAula(aula)
        }
    }

    fun atualizar(aulas: List<Aula>) {
        this.aulas.clear()
        this.aulas.addAll(aulas)
        notifyDataSetChanged()
    }

    fun deletarItem(index: Int) {
        aulas.removeAt(index)
        notifyDataSetChanged()
    }

    fun buscaItem(index: Int): Aula {
        return aulas[index];
    }

    interface ClickAula {
        fun clickAula(aula: Aula)
    }

    class ViewHolder(binding: ActivityAulaBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityAulaCard

        private val nome = binding.aulaNome
        private val horario = binding.aulaHorario
        private val turmaLocal = binding.aulaTurmaLocal
        private val professor = binding.aulaProfessor

        fun vincula(aula: Aula) {
            nome.text = aula.nome
            horario.text = aula.horarioInicio + " - " + aula.horarioFim
            turmaLocal.text = "Turma  " + aula.turma + " - Bloco " + aula.bloco + ", Sala " + aula.sala
            professor.text = "Prof. " + aula.professor
        }

    }
}