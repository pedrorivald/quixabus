package com.dev.quixabus.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.dev.quixabus.R
import com.dev.quixabus.databinding.FragmentTopBarBinding
import com.dev.quixabus.util.Navigate
import com.google.android.material.appbar.MaterialToolbar


class TopBarFragment : Fragment(R.layout.fragment_top_bar) {

    private val binding by lazy {
        FragmentTopBarBinding.inflate(layoutInflater)
    }

    private val navigate = Navigate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topAppBar: MaterialToolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener { showPopupMenu(topAppBar) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_bar, container, false)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menus, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menus_action_agenda -> {
                    navigate.toAgenda(view.context)
                    true
                }
                R.id.menus_action_itinerario -> {
                    navigate.toItinerario(view.context)
                    true
                }
                R.id.menus_action_links -> {
                    navigate.toLinksUteis(view.context)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

}