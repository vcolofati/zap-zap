package com.vcolofati.zapzap.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.vcolofati.zapzap.models.User
import com.vcolofati.zapzap.repositories.AuthRepository

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val mAuthRepository: AuthRepository = AuthRepository(application)
    var name: String? = null
    var email: String? = null
    var password: String? = null

    fun onLoginButtonClick(view: View){
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return
        }
    }
    fun onSignupButtonClick(view: View){
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
            return
        }
        signup()
    }

    private fun signup() {
        val user = User(name!!, email!!, password!!)

            this.mAuthRepository.signup(user) {

            }
    }
}