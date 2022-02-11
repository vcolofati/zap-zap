package com.vcolofati.zapzap.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.utils.startLoginActivity

class HomeViewModel: ViewModel() {

    private val repository: AuthRepository = AuthRepository()

    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }
}