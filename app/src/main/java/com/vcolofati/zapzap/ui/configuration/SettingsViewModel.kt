package com.vcolofati.zapzap.ui.configuration

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.StorageRepository
import com.vcolofati.zapzap.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SettingsViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val storageRepository = StorageRepository()

    private val user by lazy {
        authRepository.currentUser()
    }

    private val _uri: MutableLiveData<Resource<Uri?>> = MutableLiveData()

    fun save(image: Bitmap) {
        _uri.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            if (user?.uid != null) {
                val uri = storageRepository.saveImageToStorage(image, user?.uid!!)
                authRepository.updateUserProfile(uri)
                withContext(Dispatchers.Main) {
                    fetchUri()
                }
            }
        }
    }

    fun fetchUri() {
        _uri.value = Resource.sucess(user?.photoUrl)
    }

    fun getUri(): LiveData<Resource<Uri?>> {
        return _uri
    }
}