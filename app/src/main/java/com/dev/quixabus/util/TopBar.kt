package com.dev.quixabus.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.dev.quixabus.ui.fragment.TopBarFragment

class TopBar {

    private lateinit var topBarFragment: Fragment
    private lateinit var fragmentTransaction: FragmentTransaction
    fun configura(fragmentManager: FragmentManager, @IdRes id: Int) {
        topBarFragment = TopBarFragment()
        fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
            .add(id, topBarFragment)
            .disallowAddToBackStack()
            .commit()
    }
}