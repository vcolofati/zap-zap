package com.vcolofati.zapzap.ui.home

import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val user by lazy {
        this.authRepository.currentUser()
    }

    fun logout() {
        this.authRepository.logout()
    }
}