package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityComentarioBinding

class ComentarioActivity : AppCompatActivity(R.layout.activity_comentario) {

    private val binding by lazy {
        ActivityComentarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}