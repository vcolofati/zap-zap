package com.vcolofati.zapzap.ui.configuration

import android.graphics.Bitmap
import android.net.Uri
import android.opengl.GLES30
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.DatabaseRepository
import com.vcolofati.zapzap.data.repositories.StorageRepository
import com.vcolofati.zapzap.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SettingsViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val storageRepository = StorageRepository()
    private val databaseRepository = DatabaseRepository()

    private val user by lazy {
        authRepository.currentUser()
    }

    var userData = User(user?.uid, user?.displayName, user?.email,null, user?.photoUrl.toString())

    private val _uri: MutableLiveData<Resource<Uri?>> = MutableLiveData()

    fun save(image: Bitmap) {
        _uri.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            if (user?.uid != null) {
                val uri = storageRepository.saveImageToStorage(image, user?.uid!!)
                authRepository.updateUserProfile(uri)
                withContext(Dispatchers.Main) {
                    userData.imageUrl = uri.toString()
                    databaseRepository.update(userData)
                    fetchUri()
                }
            }
        }
    }

    fun updateName(view: View) {
        if (userData.name.isNullOrEmpty()) {
            return
        }
        // atualizar no usuário
        this.authRepository.updateUserProfile(userData.name!!)
        //atualizar no banco de dados
        this.databaseRepository.update(userData)
    }

    fun fetchUri() {
        _uri.value = Resource.sucess(user?.photoUrl)
    }

    fun getUri(): LiveData<Resource<Uri?>> {
        return _uri
    }
}