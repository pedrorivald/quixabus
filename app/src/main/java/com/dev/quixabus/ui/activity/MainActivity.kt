package com.dev.quixabus.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.quixabus.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToAgenda()
    }

    private fun navigateToAgenda() {
        val intent = Intent(this, AgendaActivity::class.java)
        startActivity(intent)
    }
}