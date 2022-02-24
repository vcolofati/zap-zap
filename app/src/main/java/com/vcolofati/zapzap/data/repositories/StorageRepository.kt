package com.vcolofati.zapzap.data.repositories

import android.graphics.Bitmap
import com.vcolofati.zapzap.data.firebase.FirebaseStorageSource
import javax.inject.Inject

class StorageRepository @Inject constructor(private val firebaseStorage: FirebaseStorageSource) {

    suspend fun saveImageToStorage(image: Bitmap, uid: String) = firebaseStorage.save(image, uid)
}