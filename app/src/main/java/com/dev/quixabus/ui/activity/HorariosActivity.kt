package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.ItinerarioDao
import com.dev.quixabus.databinding.ActivityHorariosBinding
import com.dev.quixabus.ui.recyclerview.adapter.ListaHorarioAdapter
import com.dev.quixabus.util.TopBar

class HorariosActivity : AppCompatActivity(R.layout.activity_horarios) {

    private val binding by lazy {
        ActivityHorariosBinding.inflate(layoutInflater)
    }

    private val dao = ItinerarioDao()
    private var adapter: ListaHorarioAdapter = ListaHorarioAdapter(this, horarios = dao.buscaHorarios())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        TopBar().configura(supportFragmentManager, R.id.activity_horarios_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        configuraBotaoVoltar()
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityHorariosRv
        recyclerView.adapter = adapter
    }

    private fun configuraBotaoVoltar() {
        val botaoVoltar = binding.activityHorariosBotaoVoltar

        botaoVoltar.setOnClickListener {
            finish()
        }
    }
}