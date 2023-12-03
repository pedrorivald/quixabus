package com.dev.quixabus.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
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
            validarPost()
        }
    }

    private fun validarPost() {
        val campoTexto = binding.activityCadastrarPostTexto
        val texto = campoTexto.text.toString().trim()

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val data = sdf.format(Date())

        val post = Post(
            data = data,
            texto = texto
        )

        if(texto.isNotEmpty()) {
            dao.salvar(post) { sucesso ->
                if(sucesso) {
                    finish()
                    Toast.makeText(this,"Post criado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível salvar o Post, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }

    }

}