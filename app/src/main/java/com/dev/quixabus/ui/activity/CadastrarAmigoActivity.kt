package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.auth.Auth
import com.dev.quixabus.dao.AmigosDao
import com.dev.quixabus.dao.UsuarioDao
import com.dev.quixabus.databinding.ActivityCadastrarAmigoBinding
import com.dev.quixabus.model.Amigo
import com.dev.quixabus.util.TopBar

class CadastrarAmigoActivity : AppCompatActivity(R.layout.activity_cadastrar_amigo) {

    private val dao = AmigosDao()
    private val usuarioDao = UsuarioDao()
    private val auth = Auth()
    private val binding by lazy {
        ActivityCadastrarAmigoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_cadastrar_amigo_fragment_top_bar)
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarAmigoBotaoSalvar

        botaoSalvar.setOnClickListener {
            val amigo = criarAmigo()
            dao.adicionar(amigo)
            finish()
        }
    }

    private fun criarAmigo(): Amigo {
        val campoEmail = binding.activityCadastrarAmigoEmail
        val email = campoEmail.text.toString()

        val idUsuarioSolicitante = auth.usuarioLogado.id

        val idUsuarioSolicitado = usuarioDao.buscaPorEmail(email).id

        return Amigo(
            id = (0..9999).random(),
            idUsuarioSolicitante = idUsuarioSolicitante,
            idUsuarioSolicitado = idUsuarioSolicitado
        )
    }
}