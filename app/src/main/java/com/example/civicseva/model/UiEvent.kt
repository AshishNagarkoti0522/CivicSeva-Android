package com.example.civicseva.model

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    data object NavigateToHome : UiEvent
}