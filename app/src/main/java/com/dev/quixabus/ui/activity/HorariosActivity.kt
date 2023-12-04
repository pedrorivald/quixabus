package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.ItinerarioDao
import com.dev.quixabus.databinding.ActivityHorariosBinding
import com.dev.quixabus.model.Horario
import com.dev.quixabus.ui.recyclerview.adapter.HorariosAdapter
import com.dev.quixabus.util.TopBar

class HorariosActivity : AppCompatActivity(R.layout.activity_horarios) {

    private val binding by lazy {
        ActivityHorariosBinding.inflate(layoutInflater)
    }

    private val dao = ItinerarioDao()
    private lateinit var adapter: HorariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopBar().configura(supportFragmentManager, R.id.activity_horarios_fragment_top_bar)

        init()
        setContentView(binding.root)
    }

    private fun init() {
        dao.buscarHorarios() { horarios ->
            if(horarios != null) {
                configuraRecyclerView(horarios)
            } else {
                configuraRecyclerView(emptyList())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        configuraBotaoVoltar()
    }

    private fun configuraRecyclerView(horarios: List<Horario>) {
        adapter = HorariosAdapter(this, horarios = horarios)
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