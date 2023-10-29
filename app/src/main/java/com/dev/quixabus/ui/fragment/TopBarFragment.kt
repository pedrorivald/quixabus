package com.dev.quixabus.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.dev.quixabus.R
import android.widget.Toast
import com.dev.quixabus.databinding.FragmentTopBarBinding
import com.google.android.material.appbar.MaterialToolbar

class TopBarFragment : Fragment(R.layout.fragment_top_bar) {

    private val binding by lazy {
        FragmentTopBarBinding.inflate(layoutInflater)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.topAppBar.setNavigationOnClickListener {
//            Toast.makeText(requireContext(), "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
//            val topAppBarView = view.findViewById<View>(R.id.topAppBar)
//            showPopupMenu(topAppBarView)
//        }
//
//        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.top_app_bar_action_perfil -> {
//                    Toast.makeText(requireContext(), "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topAppBar: MaterialToolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener { showPopupMenu(topAppBar) }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top_app_bar_action_perfil -> {
                    Toast.makeText(requireContext(), "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_bar, container, false)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menus, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menus_action_agenda -> {
                    Toast.makeText(requireContext(), "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }

        popupMenu.show()
    }

}