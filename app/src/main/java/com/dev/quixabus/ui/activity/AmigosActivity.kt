package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.auth.Auth
import com.dev.quixabus.dao.AmigosDao
import com.dev.quixabus.databinding.ActivityAmigosBinding
import com.dev.quixabus.model.AmigoItem
import com.dev.quixabus.ui.recyclerview.adapter.AmigosAdapter
import com.dev.quixabus.util.TopBar

class AmigosActivity : AppCompatActivity(R.layout.activity_amigos), AmigosAdapter.ClickRemover {

    private val binding by lazy {
        ActivityAmigosBinding.inflate(layoutInflater)
    }

    private val dao = AmigosDao()
    private val auth = Auth()
    private var adapter: AmigosAdapter = AmigosAdapter(this, amigos = dao.buscaAmigosItems(auth.usuarioLogado.id), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        TopBar().configura(supportFragmentManager, R.id.activity_amigos_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizar(dao.buscaAmigosItems(auth.usuarioLogado.id))
        configuraFab()
    }

    override fun clickRemover(amigoItem: AmigoItem) {
        dao.deletarPorIdUsuarioSolicitado(amigoItem.usuarioSolicitado.id)
        adapter.atualizar(dao.buscaAmigosItems(auth.usuarioLogado.id))
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityAmigosRv
        recyclerView.adapter = adapter
    }

    private fun configuraFab() {
        val fab = binding.activityAmigosFab
        fab.setOnClickListener {
            vaiParaCadastrarAmigo()
        }
    }

    private fun vaiParaCadastrarAmigo() {
        val intent = Intent(this, CadastrarAmigoActivity::class.java)
        startActivity(intent)
    }
}