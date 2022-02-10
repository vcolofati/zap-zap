package com.vcolofati.zapzap.repositories

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vcolofati.zapzap.models.User
import com.vcolofati.zapzap.utils.SignCallback


class AuthRepository(private val application: Application) {

    companion object {
        private val auth: FirebaseAuth = Firebase.auth

        fun getUserUuid(): String {
            return auth.currentUser!!.uid
        }
    }

    fun signup(user: User, callback: SignCallback) {
            auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(application.mainExecutor) {
                    when {
                        it.isSuccessful -> {
                            Log.i("signup", "Sucesso ao cadastrar usuario")
                            callback.onSign(it.result!!.user!!.uid)
                        }
                        else -> {
                            Log.i("signup", "Erro ao cadastrar usuario")

                        }
                    }
                }
    }

    fun signin(user: User, callback: SignCallback): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(application.mainExecutor) {
                when {
                    it.isSuccessful -> {
                        callback.onSign(it.result!!.user!!.uid)
                    }
                    else -> {}
                }
            }
    }

    fun signout() {
        auth.signOut()
    }

    fun isUserLogged(): Boolean {
        if (auth.currentUser != null) {
            return true
        }
        return false
    }
}