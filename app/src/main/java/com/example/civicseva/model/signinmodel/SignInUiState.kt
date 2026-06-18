package com.example.civicseva.model.signinmodel

import com.example.civicseva.data.signin.SignInResponse

sealed interface SignInUiState {
    data object Idle : SignInUiState
    data object Loading : SignInUiState
    data class Success(val data: SignInResponse) : SignInUiState
}