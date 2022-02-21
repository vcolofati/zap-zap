package com.vcolofati.zapzap.ui.home

import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.repositories.AuthRepository

class HomeViewModel: ViewModel() {

    private val repository: AuthRepository = AuthRepository()

    val user by lazy {
        repository.currentUser()
    }

    fun logout(){
        repository.logout()
    }
}