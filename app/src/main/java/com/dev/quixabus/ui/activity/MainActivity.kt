package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navigateToAgenda()
        navigateToItinerario()
    }

    private fun navigateToAgenda() {
        val intent = Intent(this, AgendaActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToItinerario() {
        val intent = Intent(this, ItinerarioActivity::class.java)
        startActivity(intent)
    }
}