package com.dev.quixabus.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.ComentarioDao
import com.dev.quixabus.databinding.ActivityCadastrarComentarioBinding
import com.dev.quixabus.model.Comentario
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.TopBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CadastrarComentarioActivity : AppCompatActivity(R.layout.activity_cadastrar_comentario) {

    private val dao = ComentarioDao()
    private lateinit var postId: String
    private lateinit var usuarioId: String

    private val binding by lazy {
        ActivityCadastrarComentarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postId = intent.getStringExtra("postId")!!
        usuarioId = intent.getStringExtra("usuarioId")!!

        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_cadastrar_comentario_fragment_top_bar)
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarComentarioBotaoSalvar

        botaoSalvar.setOnClickListener {
            validarComentario()
        }
    }

    private fun validarComentario() {
        val campoTexto = binding.activityCadastrarComentarioTexto
        val texto = campoTexto.text.toString().trim()

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val data = sdf.format(Date())

        if(texto.isNotEmpty()) {
            val comentario = Comentario(
                usuarioId = FirebaseHelper.getIdUser()?:"",
                data = data,
                texto = texto
            )

            dao.salvar(postId, usuarioId, comentario) { sucesso ->
                if(sucesso) {
                    finish()
                    Toast.makeText(this,"Comentário criado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível comentar, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Insira seu comentário.", Toast.LENGTH_SHORT).show()
        }


    }
}