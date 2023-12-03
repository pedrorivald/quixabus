package com.dev.quixabus.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.PostDao
import com.dev.quixabus.databinding.ActivityEditarPostBinding
import com.dev.quixabus.model.PostOld
import com.dev.quixabus.util.TopBar

class EditarPostActivity : AppCompatActivity(R.layout.activity_editar_post) {

    private var id: Int? = null
    private val dao = PostDao()
    private var post: PostOld? = null
    private val binding by lazy {
        ActivityEditarPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dados = intent.extras
        id = dados?.getInt("id")

        if (id != null) {
//            post = dao.buscarPorId(id!!)
        }

        if (post != null) {
            configuraPost(post!!)
        }

        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_editar_post_fragment_top_bar)

        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityEditarPostBotaoSalvar

        botaoSalvar.setOnClickListener {
            val post = criarPost()
            dao.atualizar(post)
            finish()
        }
    }

    private fun configuraPost(post: PostOld) {
        binding.activityEditarPostTexto.setText(post.texto)
    }

    private fun criarPost(): PostOld {
        val post = post!!
        val campoTexto = binding.activityEditarPostTexto
        val texto = campoTexto.text.toString()

        return PostOld(
            id = post.id,
            idUsuario = post.idUsuario,
            data = post.data,
            texto = texto
        )
    }

}