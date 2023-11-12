package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityHorarioBinding
import com.dev.quixabus.model.Horario

class ListaHorarioAdapter(
    private val context: Context,
    horarios: List<Horario>
): RecyclerView.Adapter<ListaHorarioAdapter.ViewHolder>() {

    private val horarios = horarios.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ActivityHorarioBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = horarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horario = horarios[position]
        holder.vincula(horario)
    }

    class ViewHolder(binding: ActivityHorarioBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityHorarioCard

        private val horario = binding.horarioHorario
        private val embarqueDesembarque = binding.horarioEmbarqueDesembarque
        private val viagens = binding.horarioViagens
        private val onibus = binding.horarioOnibus

        fun vincula(h: Horario) {
            horario.text = h.horario

            embarqueDesembarque.text = if (h.embarque && h.desembarque) {
                "Embarque / Desembarque"
            } else if(h.embarque) {
                "Embarque"
            } else if (h.desembarque) {
                "Desembarque"
            } else  {
                ""
            }

            viagens.text = "Qtd. viagens: " + h.quantidadeViagens
            onibus.text = "Responsável pelo ônibus: " + h.responsavelOnibus
        }

    }
}