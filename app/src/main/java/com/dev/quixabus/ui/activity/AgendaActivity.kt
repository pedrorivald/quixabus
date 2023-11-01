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
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityAgendaBinding
import com.dev.quixabus.ui.recyclerview.adapter.ListaAulasAdapter
import com.dev.quixabus.model.DiaSemana


class AgendaActivity : AppCompatActivity(R.layout.activity_agenda) {

    private val dao = AulasDao()
    private val adapter = ListaAulasAdapter(this, aulas = dao.buscaPorDia(DiaSemana.SEGUNDA))
    private val binding by lazy {
        ActivityAgendaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        configuraDropdownDiasDaSemana()
        setContentView(binding.root)

        binding.activityAgendaFragmentTopBar.topAppBar.setNavigationOnClickListener {
            showMenu(it, R.menu.menus)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaPorDia(DiaSemana.SEGUNDA))
        configuraFab()
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

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityAgendaRv
        recyclerView.adapter = adapter
    }

    private fun configuraDropdownDiasDaSemana() {
        val diaDaSemanaEditText = binding.activityAgendaSemana.editText as? AutoCompleteTextView
        val diasDaSemana = listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
        val adapterDiasDaSemana = ArrayAdapter(this, R.layout.dias_da_semana, diasDaSemana)

        diaDaSemanaEditText?.setAdapter(adapterDiasDaSemana)
        diaDaSemanaEditText?.setText("Segunda", false)

        diaDaSemanaEditText?.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                val diaSelecionado: String? = adapterDiasDaSemana.getItem(position)

                when (diaSelecionado) {
                    "Domingo" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.DOMINGO))
                    "Segunda" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.SEGUNDA))
                    "Terça" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.TERCA))
                    "Quarta" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.QUARTA))
                    "Quinta" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.QUINTA))
                    "Sexta" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.SEXTA))
                    "Sábado" -> adapter.atualiza(dao.buscaPorDia(DiaSemana.SABADO))
                    else -> {}
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
}