package com.example.civicseva.model.signupmodel

import com.example.civicseva.data.signup.SignupResponse

sealed interface SignupUistate {
    data object Idle : SignupUistate
    data object Loading : SignupUistate
    data class Success(val data: SignupResponse) : SignupUistate
    data class Error(val message: String) : SignupUistate
}