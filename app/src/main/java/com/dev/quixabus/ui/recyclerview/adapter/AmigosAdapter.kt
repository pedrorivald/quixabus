package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityAmigoBinding
import com.dev.quixabus.model.Usuario

class AmigosAdapter(
    private val context: Context,
    amigos: List<Usuario>,
    var clickRemover: AmigosAdapter.ClickRemover
): RecyclerView.Adapter<AmigosAdapter.ViewHolder>() {

    private val amigos = amigos.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ActivityAmigoBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = amigos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val amigo = amigos[position]
        holder.vincula(amigo)

        holder.btnRemover.setOnClickListener{
            clickRemover.clickRemover(amigo)
        }
    }

    interface ClickRemover {
        fun clickRemover(amigo: Usuario)
    }

    fun atualizar(amigos: List<Usuario>) {
        this.amigos.clear()
        this.amigos.addAll(amigos)
        notifyDataSetChanged()
    }

    fun deletarItem(index: Int) {
        amigos.removeAt(index)
        notifyDataSetChanged()
    }

    fun buscaItem(index: Int): Usuario {
        return amigos[index];
    }

    class ViewHolder(binding: ActivityAmigoBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityAmigoCard
        val btnRemover = binding.amigoRemoverBtn

        private val usuario = binding.amigoUsuario

        fun vincula(amigo: Usuario) {
            usuario.text = amigo.nome
        }

    }
}