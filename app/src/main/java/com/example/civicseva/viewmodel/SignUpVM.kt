package com.example.civicseva.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civicseva.data.ApiService
import com.example.civicseva.data.UserPreferencesRepository
import com.example.civicseva.data.signup.SignUpRequest
import com.example.civicseva.model.UiEvent
import com.example.civicseva.model.signupmodel.SignUpScreenEvent
import com.example.civicseva.model.signupmodel.SignUpUiState
import com.example.civicseva.model.signupmodel.SignUpScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpVM(
    private val apiService: ApiService,
    private val repository: UserPreferencesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var screenState by mutableStateOf(SignUpScreenState())
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
                            errorMessage = "Name is required"
                        )
                    )
                    return
                }
                if (screenState.email.value.isBlank()) {
                    screenState = screenState.copy(
                        email = screenState.email.copy(
                            errorMessage = "Email is required"
                        )
                    )
                    return
                }
                if (screenState.password.value.isBlank()) {
                    screenState = screenState.copy(
                        password = screenState.password.copy(
                            errorMessage = "Password is required"
                        )
                    )
                    return
                }
                if (!screenState.email.value.contains("@") || !screenState.email.value.contains(".")) {
                    screenState = screenState.copy(
                        email = screenState.email.copy(
                            errorMessage = "Enter a valid email address"
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
                _uiState.update { SignUpUiState.Idle }
            }
        }
    }

    private fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.update { SignUpUiState.Loading }

            try {
                val request = SignUpRequest(fullName = name, email = email, password = password)
                val response = apiService.signup(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    val accessToken = response.body()?.data?.accessToken
                    val refreshToken = response.body()?.data?.refreshToken
                    if (data != null && accessToken != null && refreshToken != null) {
                        repository.saveTokens(access = accessToken, refresh = refreshToken)

                        _uiState.update { SignUpUiState.Success(data = data) }
                        _uiEvent.send(UiEvent.NavigateToHome)

                    } else {
                        _uiEvent.send(UiEvent.ShowToast("Invalid data or tokens missing"))
                        _uiState.update { SignUpUiState.Idle }
                    }
                } else {
                    val message = response.errorBody()?.string() ?: "Something went wrong"
                    _uiEvent.send(UiEvent.ShowToast(message))
                    _uiState.update { SignUpUiState.Idle }
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.ShowToast("please check your internet connection"))
                _uiState.update { SignUpUiState.Idle }
                Log.e("SignUpVM", "signUp: ${e.localizedMessage}")
            }
        }
    }
}