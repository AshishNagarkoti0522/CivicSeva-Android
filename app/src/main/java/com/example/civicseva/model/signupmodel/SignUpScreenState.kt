package com.example.civicseva.model.signupmodel

import com.example.civicseva.model.FormField

data class SignUpScreenState (
    val name: FormField<String> = FormField(""),
    val email: FormField<String> = FormField(""),
    val password: FormField<String> = FormField("")
)