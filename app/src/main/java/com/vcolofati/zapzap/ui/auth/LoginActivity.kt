package com.vcolofati.zapzap.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.databinding.ActivityLoginBinding
import com.vcolofati.zapzap.ui.home.HomeActivity
import com.vcolofati.zapzap.utils.Status
import com.vcolofati.zapzap.utils.startHomeActivity
import com.vcolofati.zapzap.utils.toast

class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewModel
        setObservers()
    }

    private fun setObservers() {
        viewModel.response().observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                Status.SUCESS -> {
                    binding.progressbar.visibility = View.GONE
                    startHomeActivity()
                }
                Status.ERROR -> {
                    binding.progressbar.visibility = View.GONE
                    resource.message?.let { toast(it) }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            startHomeActivity()
        }
    }
}