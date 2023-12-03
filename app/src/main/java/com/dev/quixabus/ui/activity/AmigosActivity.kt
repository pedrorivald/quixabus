package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.AmigosDao
import com.dev.quixabus.databinding.ActivityAmigosBinding
import com.dev.quixabus.model.Usuario
import com.dev.quixabus.ui.recyclerview.adapter.AmigosAdapter
import com.dev.quixabus.util.TopBar

class AmigosActivity : AppCompatActivity(R.layout.activity_amigos), AmigosAdapter.ClickRemover {

    private val binding by lazy {
        ActivityAmigosBinding.inflate(layoutInflater)
    }

    private val dao = AmigosDao()
    private lateinit var adapter: AmigosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopBar().configura(supportFragmentManager, R.id.activity_amigos_fragment_top_bar)
        init()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        atualizarAdapter()
        configuraFab()
    }

    override fun clickRemover(amigo: Usuario) {
        dao.deletar(amigo.id) { sucesso ->
            if(sucesso) {
                Toast.makeText(this, "Amigo removido com sucesso!", Toast.LENGTH_SHORT).show()
                atualizarAdapter()
            }
        }
    }

    private fun init() {
        dao.buscaAmigos { amigos ->
            if(amigos != null) {
                configuraRecyclerView(amigos)
            } else {
                configuraRecyclerView(emptyList())
            }
        }
    }

    private fun atualizarAdapter() {
        dao.buscaAmigos { amigos ->
            if(amigos != null) {
                adapter.atualizar(amigos)
            } else {
                adapter.atualizar(emptyList())
            }
        }
    }

    private fun configuraRecyclerView(amigos: List<Usuario>) {
        adapter = AmigosAdapter(this, amigos = amigos, this)
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