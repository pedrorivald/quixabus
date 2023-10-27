package com.dev.quixabus.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityAgendaBinding
import com.dev.quixabus.ui.recyclerview.adapter.ListaAulasAdapter

class AgendaActivity : AppCompatActivity(R.layout.activity_agenda) {

    private val dao = AulasDao()
    private val adapter = ListaAulasAdapter(this, aulas = dao.buscaTodos())
    private val binding by lazy {
        ActivityAgendaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
        configuraFab()
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityAgendaRv
        recyclerView.adapter = adapter
    }

    private fun configuraFab() {
        val fab = binding.activityAgendaFab
        fab.setOnClickListener {
            vaiParaCadastrarAula()
        }
    }

    private fun vaiParaCadastrarAula() {
        val intent = Intent(this, CadastrarAulaActivity::class.java)
        startActivity(intent)
    }
}