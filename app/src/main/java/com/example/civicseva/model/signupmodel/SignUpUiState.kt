package com.example.civicseva.model.signupmodel

import com.example.civicseva.data.signup.SignUpResponse

sealed interface SignUpUiState {
    data object Idle : SignUpUiState
    data object Loading : SignUpUiState
    data class Success(val data: SignUpResponse) : SignUpUiState
}