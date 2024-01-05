package com.libraryrapp.whispersink.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libraryrapp.whispersink.network.FirebaseUserUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loadingState = MutableStateFlow(LoadingState.IDLE)
    val loadingState: StateFlow<LoadingState> = _loadingState

    fun logIn(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            _loadingState.value = LoadingState.LOADING
            try {
                val result = FirebaseUserUtils.logInWithEmailAndPassword(email, password)

                if (result.isSuccess) {
                    _loadingState.value = LoadingState.SUCCESS
                    home()
                } else {
                    _loadingState.value =
                        LoadingState.ERROR.copy(
                            message = result.exceptionOrNull()?.message ?: "Unknown error"
                        )
                }
            } catch (exception: Exception) {
                _loadingState.value = LoadingState.ERROR.copy(message = exception.message)
            }
        }

    fun signUp(email: String,password: String,home: () -> Unit) =
        viewModelScope.launch {
        _loadingState.value = LoadingState.LOADING
        try {
            val result = FirebaseUserUtils.createUserWithEmailAndPassword(email, password)

            if (result.isSuccess) {
                _loadingState.value = LoadingState.SUCCESS
                home()
            } else {
                _loadingState.value =
                    LoadingState.ERROR.copy(
                        message = result.exceptionOrNull()?.message ?: "Unknown error"
                    )
            }
        } catch (exception: Exception) {
            _loadingState.value = LoadingState.ERROR.copy(message = exception.message)
        }
    }


}

