package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.R
import com.dev.quixabus.dao.FeedDao
import com.dev.quixabus.dao.PostDao
import com.dev.quixabus.databinding.ActivityFeedBinding
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.ui.recyclerview.adapter.FeedAdapter
import com.dev.quixabus.ui.recyclerview.adapter.SwipeGesture
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.TopBar

class FeedActivity : AppCompatActivity(R.layout.activity_feed), FeedAdapter.ClickFeedItem, FeedAdapter.ClickVerComentarios {

    private val binding by lazy {
        ActivityFeedBinding.inflate(layoutInflater)
    }

    private val dao = FeedDao()
    private val postDao = PostDao()
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraSwipe()
        TopBar().configura(supportFragmentManager, R.id.activity_feed_fragment_top_bar)
        init()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        atualizarAdapter()
        configuraFab()
    }

    private fun init() {
        dao.buscaFeed { feed ->
            if(feed != null) {
                configuraRecyclerView(feed)
            } else {
                configuraRecyclerView(emptyList())
            }
        }
    }

    fun atualizarAdapter() {
        dao.buscaFeed { feed ->
            if(feed != null) {
                adapter.atualizar(feed)
            } else {
                adapter.atualizar(emptyList())
            }
        }
    }

    override fun clickFeedItem(feedItem: FeedItem) {
        vaiParaEditarPost(feedItem)
    }

    override fun clickVerComentarios(feedItem: FeedItem) {
        vaiParaComentarios(feedItem)
    }

    private fun configuraRecyclerView(feed: List<FeedItem>) {
        adapter = FeedAdapter(this, feedItems = feed, this, this)
        val recyclerView = binding.activityFeedRv
        recyclerView.adapter = adapter
    }

    private fun configuraSwipe() {
        val recyclerView = binding.activityFeedRv

        val swipe: SwipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).post.id
                        val idUsuario = adapter.buscaItem(position).usuario.id
                        deletar(id, idUsuario, adapter, position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).post.id
                        val idUsuario = adapter.buscaItem(position).usuario.id
                        deletar(id, idUsuario, adapter, position)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun deletar(idPost: String, idUsuario: String, adapter: FeedAdapter, position: Int) {
        if(FirebaseHelper.getIdUser() == idUsuario) {
            adapter.deletarItem(position)

            postDao.deletar(idPost) { sucesso ->
                atualizarAdapter()

                if(sucesso) {
                    Toast.makeText(this, "Post deletado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível deletar o post.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            atualizarAdapter()
            Toast.makeText(this, "Você não tem permissão para excluir esse post.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configuraFab() {
        val fab = binding.activityFeedFab
        fab.setOnClickListener {
            vaiParaCadastrarPost()
        }
    }

    private fun vaiParaCadastrarPost() {
        val intent = Intent(this, CadastrarPostActivity::class.java)
        startActivity(intent)
    }

    private fun vaiParaEditarPost(feedItem: FeedItem) {
        if(feedItem.usuario.id == FirebaseHelper.getIdUser()) {
            val intent = Intent(this, EditarPostActivity::class.java)
            intent.putExtra("postId", feedItem.post.id)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Sem permissão para editar este post.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun vaiParaComentarios(feedItem: FeedItem) {
        val intent = Intent(this, ComentariosActivity::class.java)
        intent.putExtra("postId", feedItem.post.id)
        intent.putExtra("usuarioId", feedItem.usuario.id)
        startActivity(intent)
    }
}