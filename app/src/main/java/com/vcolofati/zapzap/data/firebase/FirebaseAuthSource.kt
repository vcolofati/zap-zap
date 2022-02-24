package com.vcolofati.zapzap.data.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {


    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
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

    fun updateUserProfile(name:String) {
        val userChangeReq = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        currentUser()?.updateProfile(userChangeReq)
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}