package com.dev.quixabus.util

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
                    1
                }
                error.contains("The email address is badly formatted") -> {
                    2
                }
                error.contains("The password is invalid or the user does not have a password") -> {
                    3
                }
                error.contains("The email address is already in use by another account") -> {
                    4
                }
                error.contains("Password should be at least 6 characters") -> {
                    5
                }
                else -> {
                    6
                }
            }
        }

    }
}