package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.ComentarioDao
import com.dev.quixabus.dao.FeedDao
import com.dev.quixabus.databinding.ActivityComentariosBinding
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.ui.recyclerview.adapter.ComentariosAdapter
import com.dev.quixabus.util.TopBar

class ComentariosActivity : AppCompatActivity(R.layout.activity_comentarios) {

    private val binding by lazy {
        ActivityComentariosBinding.inflate(layoutInflater)
    }

    private var feedItem: FeedItem? = null

    private val dao = ComentarioDao()
    private val feedDao = FeedDao()
    private lateinit var adapter: ComentariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dados = intent.extras
        feedItem = dados?.get("idPost") as FeedItem

        if (feedItem != null) {
            buscarComentarios()
        }

        configuraRecyclerView()
        TopBar().configura(supportFragmentManager, R.id.activity_comentarios_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        atualizarFeed()
        configuraFab()
    }

    fun buscarComentarios() {
        feedDao.buscaComentariosPorPost(feedItem!!) { comentarios ->
            if(comentarios != null) {
                adapter = ComentariosAdapter(this, comentarios = comentarios)
            }
        }
    }

    fun atualizarFeed() {
        feedDao.buscaComentariosPorPost(feedItem!!) { comentarios ->
            if(comentarios != null) {
                adapter.atualizar(comentarios)
            }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityComentariosRv
        recyclerView.adapter = adapter
    }

    private fun configuraFab() {
        val fab = binding.activityComentariosFab
        fab.setOnClickListener {
            vaiParaCadastrarComentario()
        }
    }

    private fun vaiParaCadastrarComentario() {
        val intent = Intent(this, CadastrarComentarioActivity::class.java)
        intent.putExtra("idPost", feedItem!!.post.id)
        startActivity(intent)
    }
}