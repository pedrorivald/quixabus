package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityAmigoBinding

class AmigoActivity : AppCompatActivity(R.layout.activity_amigo) {

    private val binding by lazy {
        ActivityAmigoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}