package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.auth.Auth
import com.dev.quixabus.dao.PostDao
import com.dev.quixabus.databinding.ActivityCadastrarPostBinding
import com.dev.quixabus.model.Post
import com.dev.quixabus.util.TopBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CadastrarPostActivity : AppCompatActivity(R.layout.activity_cadastrar_post) {

    private val dao = PostDao()
    private val binding by lazy {
        ActivityCadastrarPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_cadastrar_post_fragment_top_bar)
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarPostBotaoSalvar

        botaoSalvar.setOnClickListener {
            val post = criarPost()
            dao.adicionar(post)
            finish()
        }
    }

    private fun criarPost(): Post {
        val campoTexto = binding.activityCadastrarPostTexto
        val texto = campoTexto.text.toString()

        val auth = Auth()
        val idUsuario = auth.usuarioLogado.id

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val data = sdf.format(Date())

        return Post(
            id = (0..9999).random(),
            idUsuario = idUsuario,
            data = data,
            texto = texto
        )
    }

}