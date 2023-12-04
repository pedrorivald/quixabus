package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.FeedDao
import com.dev.quixabus.databinding.ActivityComentariosBinding
import com.dev.quixabus.model.ComentarioItem
import com.dev.quixabus.ui.recyclerview.adapter.ComentariosAdapter
import com.dev.quixabus.util.TopBar

class ComentariosActivity : AppCompatActivity(R.layout.activity_comentarios) {

    private val binding by lazy {
        ActivityComentariosBinding.inflate(layoutInflater)
    }

    private lateinit var postId: String
    private lateinit var usuarioId: String

    private val feedDao = FeedDao()
    private lateinit var adapter: ComentariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postId = intent.getStringExtra("postId")!!
        usuarioId = intent.getStringExtra("usuarioId")!!

        init()

        TopBar().configura(supportFragmentManager, R.id.activity_comentarios_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        atualizarAdapter()
        configuraFab()
    }

    fun init() {
        feedDao.buscaComentariosPorPost(postId, usuarioId) { comentarios ->
            if(comentarios != null) {
                configuraRecyclerView(comentarios)
            } else {
                configuraRecyclerView(emptyList())
                Toast.makeText(this, "Este post não tem comentários ou não foi possível obte-los.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun atualizarAdapter() {
        feedDao.buscaComentariosPorPost(postId, usuarioId) { comentarios ->
            if(comentarios != null) {
                adapter.atualizar(comentarios)
            } else {
                adapter.atualizar(emptyList())
            }
        }
    }

    private fun configuraRecyclerView(comentarios: List<ComentarioItem>) {
        adapter = ComentariosAdapter(this, comentarios = comentarios)
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
        intent.putExtra("postId", postId)
        intent.putExtra("usuarioId", usuarioId)
        startActivity(intent)
    }
}