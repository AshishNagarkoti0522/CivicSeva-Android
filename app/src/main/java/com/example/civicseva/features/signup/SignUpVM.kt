package com.example.civicseva.features.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civicseva.core.network.NetworkResult
import com.example.civicseva.core.utils.SideEffects
import com.example.civicseva.data.repository.AuthRepository
import com.example.civicseva.data.signup.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignUpContract.UiState>(SignUpContract.UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<SideEffects>()
    val uiEvent = _sideEffects.receiveAsFlow()

    var state by mutableStateOf(SignUpContract.State())
        private set

    fun onEvent(event: SignUpContract.Event) {
        when(event) {
            is SignUpContract.Event.NameTyped -> {
                state = state.copy(
                    username = state.username.copy(
                        value = event.name,
                        errorMessage = null
                    )
                )
            }
            is SignUpContract.Event.EmailTyped -> {
                state = state.copy(
                    email = state.email.copy(
                        value = event.email,
                        errorMessage = null
                    )
                )
            }
            is SignUpContract.Event.PasswordTyped -> {
                state = state.copy(
                    password = state.password.copy(
                        value = event.password,
                        errorMessage = null
                    )
                )
            }
            is SignUpContract.Event.SubmitRequest -> {
                // Validation
                if (state.username.value.isBlank()) {
                    state = state.copy(
                        username = state.username.copy(
                            errorMessage = "Name is required"
                        )
                    )
                    Log.e("SignUpVM", "username is empty" )
                    return
                }
                if (state.email.value.isBlank()) {
                    state = state.copy(
                        email = state.email.copy(
                            errorMessage = "Email is required"
                        )
                    )
                    Log.e("SignUpVM", "email is empty" )
                    return
                }
                if (state.password.value.isBlank()) {
                    state = state.copy(
                        password = state.password.copy(
                            errorMessage = "Password is required"
                        )
                    )
                    Log.e("SignUpVM", "password is empty" )
                    return
                }
                if (!state.email.value.contains("@") || !state.email.value.contains(".")) {
                    state = state.copy(
                        email = state.email.copy(
                            errorMessage = "Enter a valid email address"
                        )
                    )
                    Log.e("SignUpVM", "invalid email" )
                    return
                }
                if (state.password.value.length < 6) {
                    state = state.copy(
                        password = state.password.copy(
                            errorMessage = "Password must be at least 6 characters long"
                        )
                    )
                    Log.e("SignUpVM", "weak password" )
                    return
                }
                // ApiCall
                signUp(
                    name = state.username.value.trim(),
                    password = state.password.value.trim(),
                    email = state.email.value.trim()
                )
            }
        }
    }

    private fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.update { SignUpContract.UiState.Loading }

            val request = SignUpRequest(username = name, email = email, password = password)

            when (val result = authRepository.signUp(request)) {
                is NetworkResult.Success -> {
                    _uiState.update { SignUpContract.UiState.Success }
                    _sideEffects.send(SideEffects.ShowToast("SignUp successful"))
                    Log.d("SignUpVM", "signUp: SignUp Successful")
                }
                is NetworkResult.Error -> {
                    _uiState.update { SignUpContract.UiState.Idle }
                    _sideEffects.send(SideEffects.ShowToast(result.message ?: "Error occurred"))
                    Log.e("SignUpVM", "signUp: ${result.message}")
                }
            }
        }
    }
}