package com.example.civicseva.model.signinmodel

import com.example.civicseva.model.FormField

data class SignInScreenState (
    val email: FormField<String> = FormField(""),
    val password: FormField<String> = FormField("")
)