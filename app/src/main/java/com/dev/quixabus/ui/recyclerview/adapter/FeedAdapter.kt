package com.dev.quixabus.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.databinding.ActivityPostBinding
import com.dev.quixabus.model.FeedItem

class FeedAdapter(
    private val context: Context,
    feedItems: List<FeedItem>,
    var clickFeedItem: ClickFeedItem
): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val feedItems = feedItems.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ActivityPostBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = feedItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedItem = feedItems[position]
        holder.vincula(feedItem)

        holder.cardView.setOnClickListener {
            clickFeedItem.clickFeedItem(feedItem)
        }
    }

    fun atualizar(feedItems: List<FeedItem>) {
        this.feedItems.clear()
        this.feedItems.addAll(feedItems)
        notifyDataSetChanged()
    }

    fun deletarItem(index: Int) {
        feedItems.removeAt(index)
        notifyDataSetChanged()
    }

    fun buscaItem(index: Int): FeedItem {
        return feedItems[index];
    }

    interface ClickFeedItem {
        fun clickFeedItem(feedItem: FeedItem)
    }

    class ViewHolder(binding: ActivityPostBinding): RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.activityPostCard

        private val usuario = binding.postUsuario
        private val data = binding.postData
        private val texto = binding.postTexto

        fun vincula(feedItem: FeedItem) {
            usuario.text = feedItem.usuario.nome
            data.text = feedItem.post.data
            texto.text = feedItem.post.texto
        }

    }
}