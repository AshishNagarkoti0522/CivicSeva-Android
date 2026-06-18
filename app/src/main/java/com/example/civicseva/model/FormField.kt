package com.example.civicseva.model

data class FormField<T>(
    val value: T,
    val errorMessage: String? = null
)