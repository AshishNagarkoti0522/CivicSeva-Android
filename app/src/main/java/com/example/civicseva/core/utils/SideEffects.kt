package com.example.civicseva.core.utils

sealed interface SideEffects {
    data class ShowToast(val message: String) : SideEffects
}