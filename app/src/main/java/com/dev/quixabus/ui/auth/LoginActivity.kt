package com.dev.quixabus.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityLoginBinding
import com.dev.quixabus.ui.activity.FeedActivity
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.Navigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val navigate = Navigate()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        initClicks()
        checkAuth()
    }

    private fun initClicks() {
        binding.activityLoginBotaoEntrar.setOnClickListener { validateData() }

        binding.activityLoginBotaoCriarConta.setOnClickListener {
            navigate.toCriarConta(this)
        }

        binding.activityLoginBotaoRecupear.setOnClickListener {
            navigate.toRecuperarSenha(this)
        }
    }

    private fun validateData() {
        val email = binding.activityLoginEmail.text.toString().trim()
        val senha = binding.activityLoginSenha.text.toString().trim()

        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {

                hideKeyboard()

                binding.activityLoginProgressBar.isVisible = true

                loginUser(email, senha)

            } else {
                Toast.makeText(this, "Informe uma senha!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Informe seu email!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, FeedActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, FirebaseHelper.validError(
                        task.exception?.message ?: ""
                    ), Toast.LENGTH_SHORT).show()
                }

                binding.activityLoginProgressBar.isVisible = false
            }
    }

    private fun checkAuth() {
        if (auth.currentUser != null) {
            val intent = Intent(this, FeedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
    }
}