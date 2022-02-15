package com.vcolofati.zapzap.ui.configuration

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.StorageRepository

class SettingsViewModel: ViewModel() {
    private val authRepository = AuthRepository()
    private val storageRepository = StorageRepository()

    fun save(image: Bitmap) {
        val uid = authRepository.currentUser()?.uid
        if (uid != null) {
            storageRepository.save(image, uid)
        }
    }
}