package com.vcolofati.zapzap

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vcolofati.zapzap.databinding.ActivitySignupBinding
import com.vcolofati.zapzap.viewmodels.AuthViewModel

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_signup)

        val viewModel: AuthViewModel by viewModels()
        binding.viewmodel = viewModel
    }
}