package com.vcolofati.zapzap.ui.home.fragments.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.data.repositories.DatabaseRepository

class ContactsViewModel : ViewModel() {

    private val databaseRepository = DatabaseRepository()

    private val _list = MutableLiveData<List<User>>()
    val list: LiveData<List<User>> = _list

    fun fetchUserList() {
        this.databaseRepository.getUsers(_list)
    }
}