package com.vcolofati.zapzap.di.module

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = Firebase.database

    @Provides
    @Singleton
    fun provideFirebaseStorage() = Firebase.storage
}