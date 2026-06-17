package com.example.civicseva.model.signupmodel

data class FormField<T>(
    val value: T,
    val errorMessage: String? = null
)

data class SignupScreenState (
    val name: FormField<String> = FormField(""),
    val email: FormField<String> = FormField(""),
    val password: FormField<String> = FormField("")
)