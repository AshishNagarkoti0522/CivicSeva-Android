package com.example.civicseva.features.signup

import com.example.civicseva.core.utils.FormField

class SignUpContract {
    sealed interface Event{
        class NameTyped(val name: String) : Event
        class EmailTyped(val email: String) : Event
        class PasswordTyped(val password: String) : Event
        object SubmitRequest : Event
    }

    data class State (
        val username: FormField<String> = FormField(""),
        val email: FormField<String> = FormField(""),
        val password: FormField<String> = FormField("")
    )

    sealed interface UiState {
        data object Idle : UiState
        data object Loading : UiState
        data object Success : UiState
    }
}