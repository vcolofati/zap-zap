package com.vcolofati.zapzap.data.repositories

import android.graphics.Bitmap
import com.vcolofati.zapzap.data.firebase.FirebaseStorageSource
import com.vcolofati.zapzap.data.models.User

class StorageRepository {

    private val firebaseStorage = FirebaseStorageSource()

    fun save(image: Bitmap, uid: String) = firebaseStorage.save(image, uid)
}