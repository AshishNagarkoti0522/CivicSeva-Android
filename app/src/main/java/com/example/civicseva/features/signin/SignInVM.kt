package com.example.civicseva.features.signin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civicseva.core.network.NetworkResult
import com.example.civicseva.core.utils.SideEffects
import com.example.civicseva.data.repository.AuthRepository
import com.example.civicseva.data.signin.SignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SignInContract.UiState>(SignInContract.UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<SideEffects>()
    val uiEvent = _sideEffects.receiveAsFlow()

    var state by mutableStateOf(SignInContract.State())
        private set

    fun onEvent(event: SignInContract.Event) {
        when(event) {
            is SignInContract.Event.EmailTyped -> {
                state = state.copy(
                    email = state.email.copy(
                        value = event.email,
                        errorMessage = null
                    )
                )
            }
            is SignInContract.Event.PasswordTyped -> {
                state = state.copy(
                    password = state.password.copy(
                        value = event.password,
                        errorMessage = null
                    )
                )
            }
            is SignInContract.Event.SubmitRequest -> {
                // Validation
                if (state.email.value.isBlank()) {
                    state = state.copy(
                        email = state.email.copy(
                            errorMessage = "Email is required"
                        )
                    )
                    Log.e("SignInVM", "email is empty" )
                    return
                }
                if (state.password.value.isBlank()) {
                    state = state.copy(
                        password = state.password.copy(
                            errorMessage = "Password is required"
                        )
                    )
                    Log.e("SignInVM", "password is empty" )
                    return
                }
                if (!state.email.value.contains("@") || !state.email.value.contains(".")) {
                    state = state.copy(
                        email = state.email.copy(
                            errorMessage = "Enter a valid email address"
                        )
                    )
                    Log.e("SignInVM", "invalid email" )
                    return
                }
                if (state.password.value.length < 6) {
                    state = state.copy(
                        password = state.password.copy(
                            errorMessage = "Password must be at least 6 characters long"
                        )
                    )
                    Log.e("SignInVM", "weak password" )
                    return
                }
                signIn(
                    password = state.password.value.trim(),
                    email = state.email.value.trim()
                )
            }
        }
    }

    private fun signIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.update { SignInContract.UiState.Loading }

            val request = SignInRequest(email = email, password = password)

            when(val result = authRepository.signIn(request)) {
                is NetworkResult.Success -> {
                    _uiState.update { SignInContract.UiState.Success }
                    _sideEffects.send(SideEffects.ShowToast("SignIn successful"))
                    Log.d("SignInVM", "signIn: SignIn Successful")
                }
                is NetworkResult.Error -> {
                    _uiState.update { SignInContract.UiState.Idle }
                    _sideEffects.send(SideEffects.ShowToast(result.message ?: "Error occurred"))
                    Log.e("SignInVM", "signIn: ${result.message}")
                }
            }
        }
    }
}