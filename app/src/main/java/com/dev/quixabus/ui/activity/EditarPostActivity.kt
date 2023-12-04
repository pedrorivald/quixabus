package com.dev.quixabus.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.PostDao
import com.dev.quixabus.databinding.ActivityEditarPostBinding
import com.dev.quixabus.model.Post
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.TopBar

class EditarPostActivity : AppCompatActivity(R.layout.activity_editar_post) {

    private lateinit var postId: String
    private lateinit var post: Post
    private val dao = PostDao()
    private val binding by lazy {
        ActivityEditarPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postId = intent.getStringExtra("postId")!!
        buscaPost()

        configuraBotaoSalvar()
        TopBar().configura(supportFragmentManager, R.id.activity_editar_post_fragment_top_bar)

        setContentView(binding.root)
    }

    private fun buscaPost() {
        dao.buscarPorId(postId, FirebaseHelper.getIdUser()?:"") { post ->
            if(post != null) {
                this.post = Post(
                    id = post.id,
                    texto = post.texto,
                    data = post.data
                )
                configuraPost(this.post)
            } else {
                Toast.makeText(this, "Não foi possível obter o post.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityEditarPostBotaoSalvar

        botaoSalvar.setOnClickListener {
            validarPost()
        }
    }

    private fun configuraPost(post: Post) {
        binding.activityEditarPostTexto.setText(post.texto)
    }

    private fun validarPost() {
        val campoTexto = binding.activityEditarPostTexto
        val texto = campoTexto.text.toString().trim()

        if(texto.isNotEmpty()) {
            dao.atualizar(postId, texto, FirebaseHelper.getIdUser()?:"") { sucesso ->
                hideKeyboard()

                if(sucesso) {
                    finish()
                    Toast.makeText(this, "Post editado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível editar o post, tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(this, "Digite o que achou da sua viagem!", Toast.LENGTH_SHORT).show()
        }


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
    }

}