package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.auth.Auth
import com.dev.quixabus.dao.ComentarioDao
import com.dev.quixabus.dao.FeedDao
import com.dev.quixabus.databinding.ActivityComentariosBinding
import com.dev.quixabus.ui.recyclerview.adapter.ComentariosAdapter
import com.dev.quixabus.util.TopBar

class ComentariosActivity : AppCompatActivity(R.layout.activity_comentarios) {

    private val binding by lazy {
        ActivityComentariosBinding.inflate(layoutInflater)
    }

    private var idPost: Int? = null

    private val auth = Auth()
    private val dao = ComentarioDao()
    private val feedDao = FeedDao()
    private var adapter: ComentariosAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dados = intent.extras
        idPost = dados?.getInt("idPost")

        if (idPost != null) {
            adapter = ComentariosAdapter(this, comentarios = feedDao.buscaComentariosPorPost(idPost!!))
        }

        configuraRecyclerView()
        TopBar().configura(supportFragmentManager, R.id.activity_comentarios_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter?.atualizar(feedDao.buscaComentariosPorPost(idPost!!))
        configuraFab()
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
        intent.putExtra("idPost", idPost)
        startActivity(intent)
    }
}