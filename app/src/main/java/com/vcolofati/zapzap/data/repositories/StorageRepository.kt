package com.vcolofati.zapzap.data.repositories

import android.graphics.Bitmap
import com.vcolofati.zapzap.data.firebase.FirebaseStorageSource

class StorageRepository {

    private val firebaseStorage = FirebaseStorageSource()

    suspend fun saveImageToStorage(image: Bitmap, uid: String) = firebaseStorage.save(image, uid)
}