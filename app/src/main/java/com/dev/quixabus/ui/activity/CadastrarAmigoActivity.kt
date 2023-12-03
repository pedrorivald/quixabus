package com.dev.quixabus.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.AmigosDao
import com.dev.quixabus.databinding.ActivityCadastrarAmigoBinding
import com.dev.quixabus.util.TopBar

class CadastrarAmigoActivity : AppCompatActivity(R.layout.activity_cadastrar_amigo) {

    private val dao = AmigosDao()
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
            criarAmigo()
        }
    }

    private fun criarAmigo() {
        val campoEmail = binding.activityCadastrarAmigoEmail
        val email = campoEmail.text.toString().trim()

        if(email.isNotEmpty()) {
            dao.salvar(email) { sucesso ->
                if(sucesso) {
                    campoEmail.setText("")
                    hideKeyboard()
                    Toast.makeText(this, "Amizade realizada!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível concluir a solicitação, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Preencha o campo de email.", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
    }
}