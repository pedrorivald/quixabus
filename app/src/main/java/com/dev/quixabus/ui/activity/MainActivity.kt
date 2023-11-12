package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.util.TopBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopBar().configura(supportFragmentManager, R.id.activity_main_fragment_top_bar)
        setContentView(R.layout.activity_main)
    }
}