package com.vcolofati.zapzap.data.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable
import kotlinx.coroutines.tasks.await

class FirebaseAuthSource {


    companion object {
        private var firebaseAuth: FirebaseAuth? = null
            get() {
                if (field == null) {
                    firebaseAuth = Firebase.auth
                }
                return field
            }
    }


    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    suspend fun updateUserProfile(uri: Uri?) {
        val userChangeReq = UserProfileChangeRequest.Builder()
            .setPhotoUri(uri)
            .build()
         currentUser()?.updateProfile(userChangeReq)?.await()
    }

    fun logout() = firebaseAuth?.signOut()

    fun currentUser() = firebaseAuth?.currentUser
}