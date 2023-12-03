package com.dev.quixabus.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityCriarContaBinding
import com.dev.quixabus.model.Usuario
import com.dev.quixabus.ui.activity.FeedActivity
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.Navigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class CriarContaActivity : AppCompatActivity(R.layout.activity_criar_conta) {

    private val binding by lazy {
        ActivityCriarContaBinding.inflate(layoutInflater)
    }

    private val navigate = Navigate()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.activityCriarContaBotaoCriarConta.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.activityCriarContaEmail.text.toString().trim()
        val senha = binding.activityCriarContaSenha.text.toString().trim()
        val nome = binding.activityCriarContaNome.text.toString().trim()

        if(nome.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (senha.isNotEmpty()) {

                    hideKeyboard()

                    binding.activityCriarContaProgressBar.isVisible = true

                    registerUser(email, senha, nome)

                } else {
                    Toast.makeText(this, "Informe uma senha!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Informe seu email!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Informe seu nome!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, senha: String, nome: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = nome
                    }

                    val usuario = Usuario(
                        id = FirebaseHelper.getIdUser()?: "",
                        nome = nome,
                        email = email
                    )

                    salvarUsuario(usuario)

                } else {
                    Toast.makeText(this, FirebaseHelper.validError(
                        task.exception?.message ?: ""
                    ), Toast.LENGTH_SHORT).show()

                    binding.activityCriarContaProgressBar.isVisible = false
                }
            }
    }

    private fun salvarUsuario(usuario: Usuario) {
        FirebaseHelper.getDatabase()
            .child("usuarios")
            .child(usuario.id)
            .setValue(usuario)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, FeedActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Não foi possível criar sua conta, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }

                binding.activityCriarContaProgressBar.isVisible = false

            }.addOnFailureListener {
                Toast.makeText(this, "Não foi possível criar sua conta, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                binding.activityCriarContaProgressBar.isVisible = false
            }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
    }
}