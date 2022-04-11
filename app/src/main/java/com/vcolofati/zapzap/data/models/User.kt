package com.vcolofati.zapzap.data.models

import com.google.firebase.database.Exclude
import java.io.Serializable

class User (@get:Exclude var uid: String? = null, var name: String? = null, var email: String? = null,
            @get:Exclude var password: String? = null, var imageUrl: String? = null): Serializable {

    @Exclude
    fun convertClassToMap(): Map<String, String?> {
        return mapOf("email" to this.email, "name" to this.name, "imageUrl" to this.imageUrl)
    }

}