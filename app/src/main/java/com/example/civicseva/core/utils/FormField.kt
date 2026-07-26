package com.example.civicseva.core.utils

data class FormField<T>(
    val value: T,
    val errorMessage: String? = null
)