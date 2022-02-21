package com.vcolofati.zapzap.data.firebase

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class FirebaseStorageSource {
    companion object {
        private var firebaseStorage: FirebaseStorage? = null
            get() {
                if (field == null) {
                    firebaseStorage = Firebase.storage
                }
                return field
            }
    }

    private val storageRef by lazy { firebaseStorage?.reference }

    suspend fun save(image: Bitmap, uid: String): Uri? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 70, baos)
        val imageData = baos.toByteArray()
        val imageRef = storageRef?.child("imagens")
            ?.child("perfil")
            ?.child("${uid}.png")

        imageRef?.putBytes(imageData)?.await()
        return imageRef?.downloadUrl?.await()
    }
}