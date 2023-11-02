package com.dev.quixabus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityAulaBinding

class AulaActivity : AppCompatActivity(R.layout.activity_aula) {

    private val binding by lazy {
        ActivityAulaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}