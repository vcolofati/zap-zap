package com.vcolofati.zapzap.data.firebase

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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

    fun save(image: Bitmap, uid: String) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val imageData = baos.toByteArray()
        val imageRef = storageRef?.child("imagens")
            ?.child("perfil")
            ?.child("${uid}.jpeg")

        val uploadTask = imageRef?.putBytes(imageData)
        uploadTask?.addOnFailureListener {
            Log.i("storage", "Falha ao fazer upload da imagem")
        }?.addOnSuccessListener {
            Log.i("storage", "Sucesso ao fazer upload da imagem")

        }
    }
}