package com.vcolofati.zapzap.data.models

import com.google.firebase.database.Exclude

class User (@get:Exclude var uid: String? = null, var name: String? = null, var email: String? = null,
            @get:Exclude var password: String? = null)