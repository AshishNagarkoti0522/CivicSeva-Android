package com.example.civicseva.features.signin

import com.example.civicseva.core.utils.FormField
import com.example.civicseva.data.signin.SignInResponse

class SignInContract {
    sealed interface Event {
        class EmailTyped(val email: String) : Event
        class PasswordTyped(val password: String) : Event
        object SubmitRequest : Event
    }

    data class State (
        val email: FormField<String> = FormField(""),
        val password: FormField<String> = FormField("")
    )

    sealed interface UiState {
        data object Idle : UiState
        data object Loading : UiState
        data object Success : UiState
    }
}