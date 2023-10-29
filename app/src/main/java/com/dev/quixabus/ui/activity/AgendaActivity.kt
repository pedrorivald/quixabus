package com.dev.quixabus.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityAgendaBinding
import com.dev.quixabus.ui.recyclerview.adapter.ListaAulasAdapter
import com.google.android.material.appbar.MaterialToolbar

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

        binding.activityAgendaFragmentTopBar.topAppBar.setNavigationOnClickListener {
            showMenu(it, R.menu.menus)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
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