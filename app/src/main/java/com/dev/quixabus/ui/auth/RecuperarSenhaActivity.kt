package com.dev.quixabus.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dev.quixabus.R
import com.dev.quixabus.databinding.ActivityRecuperarSenhaBinding
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.Navigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarSenhaActivity : AppCompatActivity(R.layout.activity_recuperar_senha) {

    private val binding by lazy {
        ActivityRecuperarSenhaBinding.inflate(layoutInflater)
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
        binding.activityRecuperarBotaoEnviar.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.activityRecuperarEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            hideKeyboard()

            binding.activityRecuperarProgressBar.isVisible = true

            recoverAccountUser(email)
        } else {
            Toast.makeText(this, "Informe seu email!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Enviamos um link de confirmação para seu Email.", Toast.LENGTH_SHORT).show()
                    navigate.toLogin(this)
                } else {
                    Toast.makeText(this, FirebaseHelper.validError(
                        task.exception?.message ?: ""
                    ), Toast.LENGTH_SHORT).show()
                }

                binding.activityRecuperarProgressBar.isVisible = false
            }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
    }
}