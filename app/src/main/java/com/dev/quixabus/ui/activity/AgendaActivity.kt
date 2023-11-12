package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityAgendaBinding
import com.dev.quixabus.model.Aula
import com.dev.quixabus.model.DiaSemana
import com.dev.quixabus.ui.recyclerview.adapter.ListaAulasAdapter
import com.dev.quixabus.ui.recyclerview.adapter.SwipeGesture
import com.dev.quixabus.util.TopBar


class AgendaActivity : AppCompatActivity(R.layout.activity_agenda), ListaAulasAdapter.ClickAula {

    private val binding by lazy {
        ActivityAgendaBinding.inflate(layoutInflater)
    }

    private var diaSelecionado: String? = null
    private val dao = AulasDao()
    private var adapter: ListaAulasAdapter = ListaAulasAdapter(this, aulas = dao.buscaPorDia(diaSemanaSelecionado(diaSelecionado)), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        configuraSwipe()
        configuraDropdownDiasDaSemana()
        TopBar().configura(supportFragmentManager, R.id.activity_agenda_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizar(dao.buscaPorDia(diaSemanaSelecionado(diaSelecionado)))
        configuraFab()
    }

    override fun clickAula(aula: Aula) {
        vaiParaEditarAula(aula.id)
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menus_action_agenda -> {
                    Toast.makeText(this, "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }

        popup.show()
    }

    private fun navagarMenu() {

    }

    private fun configuraRecyclerView() {
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
                        adapter.deletarItem(position)
                        dao.deletar(id)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).id
                        adapter.deletarItem(position)
                        dao.deletar(id)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(recyclerView)
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
                adapter.atualizar(dao.buscaPorDia(diaSemanaSelecionado(diaSelecionado)))
            }
    }

    private fun diaSemanaSelecionado(diaText: String?): DiaSemana {
        return when (diaText) {
            "Domingo" -> DiaSemana.DOMINGO
            "Segunda" -> DiaSemana.SEGUNDA
            "Terça" -> DiaSemana.TERCA
            "Quarta" -> DiaSemana.QUARTA
            "Quinta" -> DiaSemana.QUINTA
            "Sexta" -> DiaSemana.SEXTA
            "Sábado" -> DiaSemana.SABADO
            else -> {
                DiaSemana.SEGUNDA
            }
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

    private fun vaiParaEditarAula(id: Int) {
        val intent = Intent(this, EditarAulaActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}