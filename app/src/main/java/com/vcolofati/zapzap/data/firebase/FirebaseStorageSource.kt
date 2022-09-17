package com.vcolofati.zapzap.data.firebase

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class FirebaseStorageSource @Inject constructor(firebaseStorage: FirebaseStorage) {

    private val storageRef by lazy { firebaseStorage.reference }

    suspend fun saveProfileImageToStorage(image: Bitmap, uid: String): Uri? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 70, baos)
        val imageData = baos.toByteArray()
        val imageRef = storageRef.child("imagens")
            .child("perfil")
            .child("${uid}.png")

        imageRef.putBytes(imageData).await()
        return imageRef.downloadUrl.await()
    }

    suspend fun saveImageToStorage(image: Bitmap, uid: String): Uri? {
        val imageName: String = UUID.randomUUID().toString()
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 25, baos)
        val imageData = baos.toByteArray()
        val imageRef = storageRef.child("imagens")
            .child("mensagens")
            .child(uid)
            .child(imageName)

        imageRef.putBytes(imageData).await()
        return imageRef.downloadUrl.await()
    }
}