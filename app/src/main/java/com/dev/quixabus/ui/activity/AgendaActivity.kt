package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityAgendaBinding
import com.dev.quixabus.model.Aula
import com.dev.quixabus.ui.recyclerview.adapter.AulasAdapter
import com.dev.quixabus.ui.recyclerview.adapter.SwipeGesture
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.TopBar


class AgendaActivity : AppCompatActivity(R.layout.activity_agenda), AulasAdapter.ClickAula {

    private val binding by lazy {
        ActivityAgendaBinding.inflate(layoutInflater)
    }

    private var diaSelecionado: String = "Segunda"
    private val dao = AulasDao()
    private lateinit var adapter: AulasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraSwipe()
        configuraDropdownDiasDaSemana()
        TopBar().configura(supportFragmentManager, R.id.activity_agenda_fragment_top_bar)

        init()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        atualizarAdapter()
        configuraFab()
    }

    private fun init() {
        dao.buscarPorDia(FirebaseHelper.getIdUser()?:"", diaSelecionado) { aulas ->
            if(aulas != null) {
                configuraRecyclerView(aulas)
            } else {
                configuraRecyclerView(emptyList())
            }
        }
    }

    fun atualizarAdapter() {
        dao.buscarPorDia(FirebaseHelper.getIdUser()?:"", diaSelecionado) { aulas ->
            if(aulas != null) {
                adapter.atualizar(aulas)
            } else {
                adapter.atualizar(emptyList())
            }
        }
    }

    override fun clickAula(aula: Aula) {
        vaiParaEditarAula(aula.id)
    }

    private fun configuraRecyclerView(aulas: List<Aula>) {
        adapter = AulasAdapter(this, aulas = aulas, this)
        val recyclerView = binding.activityAgendaRv
        recyclerView.adapter = adapter
    }

    private fun configuraSwipe() {
        val recyclerView = binding.activityAgendaRv

        val swipe: SwipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).id
                        deletar(id, adapter, position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).id
                        deletar(id, adapter, position)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun deletar(aulaId: String, adapter: AulasAdapter, position: Int) {
        adapter.deletarItem(position)

        dao.deletar(aulaId) { sucesso ->
            atualizarAdapter()

            if(sucesso) {
                Toast.makeText(this, "Aula deletada com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Não foi possível deletar a aula.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configuraDropdownDiasDaSemana() {
        val diasDaSemana = listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
        val diaDaSemanaEditText = binding.activityAgendaSemana.editText as? AutoCompleteTextView
        val adapterDiasDaSemana = ArrayAdapter(this, R.layout.dias_da_semana, diasDaSemana)

        diaDaSemanaEditText?.setAdapter(adapterDiasDaSemana)
        diaDaSemanaEditText?.setText("Segunda", false)

        diaDaSemanaEditText?.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                diaSelecionado = adapterDiasDaSemana.getItem(position).toString()
                atualizarAdapter()
            }
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

    private fun vaiParaEditarAula(id: String) {
        val intent = Intent(this, EditarAulaActivity::class.java)
        intent.putExtra("aulaId", id)
        startActivity(intent)
    }
}