package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityComentarioBinding
import com.dev.quixabus.model.ComentarioItem

class ComentariosAdapter(
    private val context: Context,
    comentarios: List<ComentarioItem>
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
    }

    fun atualizar(comentarios: List<ComentarioItem>) {
        this.comentarios.clear()
        this.comentarios.addAll(comentarios)
        notifyDataSetChanged()
    }

    fun deletarItem(index: Int) {
        comentarios.removeAt(index)
        notifyDataSetChanged()
    }

    fun buscaItem(index: Int): ComentarioItem {
        return comentarios[index];
    }

    class ViewHolder(binding: ActivityComentarioBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityComentarioCard

        private val usuario = binding.comentarioUsuario
        private val data = binding.comentarioData
        private val texto = binding.comentarioTexto

        fun vincula(comentario: ComentarioItem) {
            usuario.text = comentario.usuario.nome
            data.text = comentario.comentario.data
            texto.text = comentario.comentario.texto
        }

    }
}