package com.vcolofati.zapzap.ui.home.fragments.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
    ) : ViewModel() {

    val user by lazy {
        authRepository.currentUser()
    }

    private val _list = MutableLiveData<List<User>>()
    val list: LiveData<List<User>> = _list

    fun fetchUserList() {
        user?.email?.let { this.databaseRepository.getUsers(_list, it) }
    }
}