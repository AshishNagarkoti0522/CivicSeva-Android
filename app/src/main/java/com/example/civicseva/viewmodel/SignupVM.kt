package com.example.civicseva.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civicseva.data.ApiService
import com.example.civicseva.data.UserPreferencesRepository
import com.example.civicseva.data.signup.SignupRequest
import com.example.civicseva.model.signupmodel.SignUpScreenEvent
import com.example.civicseva.model.signupmodel.SignupUistate
import com.example.civicseva.model.signupmodel.SignupScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupVM(
    private val apiService: ApiService,
    private val repository: UserPreferencesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignupUistate>(SignupUistate.Idle)
    val uiState = _uiState.asStateFlow()

    var screenState by mutableStateOf(SignupScreenState())
        private set

    fun onEvent(event: SignUpScreenEvent) {
        when(event) {
            is SignUpScreenEvent.NameTyped -> {
                screenState = screenState.copy(
                    name = screenState.name.copy(
                        value = event.name,
                        errorMessage = null
                    )
                )
            }
            is SignUpScreenEvent.EmailTyped -> {
                screenState = screenState.copy(
                    email = screenState.email.copy(
                        value = event.email,
                        errorMessage = null
                    )
                )
            }
            is SignUpScreenEvent.PasswordTyped -> {
                screenState = screenState.copy(
                    password = screenState.password.copy(
                        value = event.password,
                        errorMessage = null
                    )
                )
            }
            is SignUpScreenEvent.SubmitRequest -> {
                if (screenState.name.value.isBlank()) {
                    screenState = screenState.copy(
                        name = screenState.name.copy(
                            errorMessage = "Name cannot bhi empty"
                        )
                    )
                    return
                }
                if (screenState.email.value.isBlank()) {
                    screenState = screenState.copy(
                        email = screenState.email.copy(
                            errorMessage = "Email cannot bhi empty"
                        )
                    )
                    return
                }
                if (screenState.password.value.isBlank()) {
                    screenState = screenState.copy(
                        password = screenState.password.copy(
                            errorMessage = "Password cannot bhi empty"
                        )
                    )
                    return
                }
                if (!screenState.email.value.contains("@") || !screenState.email.value.contains(".")) {
                    screenState = screenState.copy(
                        email = screenState.email.copy(
                            errorMessage = "Invalid email"
                        )
                    )
                    return
                }
                if (screenState.password.value.length < 6) {
                    screenState = screenState.copy(
                        password = screenState.password.copy(
                            errorMessage = "Password must be at least 6 characters long"
                        )
                    )
                    return
                }
                signUp(
                    name = screenState.name.value.trim(),
                    password = screenState.password.value.trim(),
                    email = screenState.email.value.trim()
                )
            }
            is SignUpScreenEvent.Retry -> {
                _uiState.update { SignupUistate.Idle }
            }
        }
    }

    private fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.update { SignupUistate.Loading }

            try {
                val request = SignupRequest(fullName = name, email = email, password = password)
                val response = apiService.signup(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    val token = response.body()?.data?.accessToken
                    if (token != null) {
                        repository.saveToken(token)
                        if (data != null) {
                            _uiState.update { SignupUistate.Success(data = data) }
                        } else {
                            _uiState.update { SignupUistate.Error("no user data") }
                        }
                    } else {
                        _uiState.update { SignupUistate.Error("Token missing") }
                    }
                } else {
                    val message = response.errorBody()?.string() ?: "Something went wrong"
                    _uiState.update { SignupUistate.Error(message) }
                }
            } catch (e: Exception) {
                _uiState.update { SignupUistate.Error("please check your internet connection") }
                Log.e("SignupVM", "signUp: ${e.localizedMessage}")
            }
        }
    }
}