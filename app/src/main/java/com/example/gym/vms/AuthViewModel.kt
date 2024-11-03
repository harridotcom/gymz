package com.example.gymz.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    var auth = FirebaseAuth.getInstance()
    private var _authState = MutableLiveData<AuthState>()
    var authState: LiveData<AuthState> = _authState

    init {
        checkStatus()
    }

    fun checkStatus(){
        if (auth.currentUser != null){
            _authState.value = AuthState.Authenticated
        }else{
            _authState.value = AuthState.UnAuthenticated
        }
    }

    fun login(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Password and Email are Required")
        }else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _authState.value = AuthState.Authenticated
                    }else{
                        _authState.value = AuthState.Error("Try Later")
                    }
                }
        }
    }

    fun register(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Password and Email are Required")
        }else{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _authState.value = AuthState.Authenticated
                    }else{
                        _authState.value = AuthState.Error("Try Later")
                    }
                }
        }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }
}

sealed class AuthState{
    object Authenticated: AuthState()
    object UnAuthenticated: AuthState()
    data class Error(val message: String): AuthState()
}