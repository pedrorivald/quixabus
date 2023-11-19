package com.dev.quixabus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityHorariosBinding
import android.widget.Button
import com.dev.quixabus.util.TopBar
import android.content.Intent
import android.net.Uri
import com.dev.quixabus.databinding.ActivityLinksBinding

class LinksActivity : AppCompatActivity(R.layout.activity_links) {
    private val binding by lazy {
        ActivityLinksBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        TopBar().configura(supportFragmentManager,R.id.activity_links_fragment_top_bar)

        val buttonSigaa: Button = binding.buttonSigaa
        val buttonSippa: Button = binding.buttonSippa
        val buttonMoodle: Button = binding.buttonMoodle
        val buttonRU: Button = binding.buttonRU
        val buttonUFC: Button = binding.buttonUFC

        buttonSigaa.setOnClickListener {
            openUrl("https://si3.ufc.br/sigaa/verTelaLogin.do")
        }

        buttonSippa.setOnClickListener {
            openUrl("https://sistemas.quixada.ufc.br/sippa/")
        }

        buttonRU.setOnClickListener {
            openUrl("https://si3.ufc.br/public//jsp/restaurante_universitario/consulta_comensal_ru.jsf")
        }

        buttonUFC.setOnClickListener {
            openUrl("https://www.quixada.ufc.br/campus/")
        }

        buttonMoodle.setOnClickListener {
            openUrl("https://moodle2.quixada.ufc.br/login/index.php")
        }

    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}