package com.dev.quixabus.util

import com.dev.quixabus.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    companion object {

        fun getDatabase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun isAutenticated() = getAuth().currentUser != null

        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.nenhuma_conta_encontrada
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.insira_email_valido
                }
                error.contains("The supplied auth credential is incorrect") -> {
                    R.string.senha_invalida
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_existente
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.insira_uma_senha_forte
                }
                else -> {
                    R.string.erro_global
                }
            }
        }

    }
}