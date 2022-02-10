package com.vcolofati.zapzap

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vcolofati.zapzap.databinding.ActivityLoginBinding
import com.vcolofati.zapzap.viewmodels.AuthViewModel

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)
        val viewModel: AuthViewModel by viewModels()
        binding.viewmodel = viewModel
        binding.ui = this
    }

    fun redirectToSignup(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}