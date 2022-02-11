package com.vcolofati.zapzap.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.utils.Resource
import com.vcolofati.zapzap.utils.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class AuthViewModel: ViewModel() {

    private val repository: AuthRepository = AuthRepository()

    // variaveis da ui
    var name: String? = null
    var email: String? = null
    var password: String? = null

    private val response: MutableLiveData<Resource<Status>> = MutableLiveData()

    // disposable para eliminar o Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    fun login() {

        // validando email e senha
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            response.postValue(Resource.error("Preencha todos os campos"))
            return
        }

        // autenticação começou
        response.postValue(Resource.loading())

        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // postando sucesso para o observer
                response.postValue(Resource.sucess())
            }, {
                val message = when(it) {
                    is FirebaseAuthInvalidUserException -> "Usuário não cadastrado"
                    is FirebaseAuthInvalidCredentialsException -> "E-mail e senha não correspondem a um usuário cadastrado"
                    else -> "Erro ao cadastrar usuário: ${it.message}"
                }
                // postando erro para o observer
                response.postValue(Resource.error(message))
            })
        disposables.add(disposable)
    }

    fun signup() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
            response.postValue(Resource.error("Preencha todos os campos"))
            return
        }
        response.postValue(Resource.loading())
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                response.postValue(Resource.sucess())
            }, {
                val message = when(it) {
                    is FirebaseAuthWeakPasswordException -> "Digite uma senha mais forte"
                    is FirebaseAuthInvalidCredentialsException -> "Blabla"
                    is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada"
                    else -> "Erro ao cadastrar usuário: ${it.message}"
                }
                response.postValue(Resource.error(message))
            })
        disposables.add(disposable)
    }

    fun response(): LiveData<Resource<Status>> = response

    fun goToSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    // Eliminando os disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}