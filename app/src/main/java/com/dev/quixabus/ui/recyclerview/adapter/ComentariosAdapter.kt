package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityComentarioBinding
import com.dev.quixabus.model.Comentario

class ComentariosAdapter(
    private val context: Context,
    comentarios: List<Comentario>,
    var clickAula: ClickComentario
): RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {

    private val comentarios = comentarios.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ActivityComentarioBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = comentarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comentario = comentarios[position]
        holder.vincula(comentario)

        holder.cardView.setOnClickListener {
            clickAula.clickComentario(comentario)
        }
    }

    fun atualizar(comentarios: List<Comentario>) {
        this.comentarios.clear()
        this.comentarios.addAll(comentarios)
        notifyDataSetChanged()
    }

    fun deletarItem(index: Int) {
        comentarios.removeAt(index)
        notifyDataSetChanged()
    }

    fun buscaItem(index: Int): Comentario {
        return comentarios[index];
    }

    interface ClickComentario {
        fun clickComentario(comentario: Comentario)
    }

    class ViewHolder(binding: ActivityComentarioBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityComentarioCard

        fun vincula(comentario: Comentario) {

        }

    }
}