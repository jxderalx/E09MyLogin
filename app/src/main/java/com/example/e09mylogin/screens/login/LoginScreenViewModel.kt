package com.example.e09mylogin.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e09mylogin.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    //la estaremos usando a lo largo del proyecto
    private val auth: FirebaseAuth = Firebase.auth
    //impide que se creen varios usuarios accidentalmente
    private val _loading = MutableLiveData(false)

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task -> //si la tarea tuve exito escribimos mensaje en log
                        if (task.isSuccessful) {
                            Log.d("MyLogin", "Google logueado!!!!")
                            home()
                        } else {
                            Log.d(
                                "MyLogin",
                                "signInWithGoogle: ${task.result.toString()}"
                            )
                        }
                    }
            } catch (ex: Exception) {
                Log.d("MyLogin", "Error al loguear con Google: ${ex.message}")
            }
        }

    fun signInWithFacebook(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task -> //si la tarea tuve exito escribimos mensaje en log
                        if (task.isSuccessful) {
                            Log.d("MyLogin", "Facebook logueado!!!!")
                            home()
                        } else {
                            Log.d(
                                "MyLogin",
                                "signInWithFacebook: ${task.result.toString()}"
                            )
                        }
                    }
            } catch (ex: Exception) {
                Log.d("MyLogin", "Error al loguear con Facebook: ${ex.message}")
            }
        }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyLogin", "...signWithEmailAndPassword logueado...!!!")
                            home()
                        } else {
                            Log.d(
                                "MyLogin",
                                "signInWithEmailAndPassword: ${task.result.toString()}"
                            )
                        }
                    }

            } catch (ex: Exception){
                Log.d("MyLogin", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) { //para que no vuelva a entrar accidentalmente y cree muchos usuarios
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName =
                            task.result?.user?.email?.split("@")
                                ?.get(0)
                        createUser(displayName)
                        home()
                    } else {
                        Log.d(
                            "MyLogin",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid //sacamos la Id del auth

        //usando un data class
        val user = User (
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "xd",
            profession = "Android Developer",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance()
            .collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("MyLogin", "Creado ${it.id}")
            }
            .addOnFailureListener {
                Log.d("MyLogin", "Error al crear: ${it}")
            }
    }


}

