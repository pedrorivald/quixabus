package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.ComentarioDao
import com.dev.quixabus.databinding.ActivityCadastrarComentarioBinding
import com.dev.quixabus.model.ComentarioOld
import com.dev.quixabus.util.TopBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CadastrarComentarioActivity : AppCompatActivity(R.layout.activity_cadastrar_comentario) {

    private val dao = ComentarioDao()

    private var idPost: Int? = null

    private val binding by lazy {
        ActivityCadastrarComentarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dados = intent.extras
        idPost = dados?.getInt("idPost")

        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_cadastrar_comentario_fragment_top_bar)
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarComentarioBotaoSalvar

        botaoSalvar.setOnClickListener {
            val comentario = criarComentario()
            dao.adicionar(comentario)
            finish()
        }
    }

    private fun criarComentario(): ComentarioOld {
        val campoTexto = binding.activityCadastrarComentarioTexto
        val texto = campoTexto.text.toString()

        val idUsuario = 1

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val data = sdf.format(Date())

        return ComentarioOld(
            id = (0..9999).random(),
            idUsuario = idUsuario,
            idPost = idPost!!,
            data = data,
            texto = texto
        )
    }
}