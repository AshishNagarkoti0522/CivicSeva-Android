package com.example.civicseva.model.signupmodel

sealed interface SignUpScreenEvent{
    class NameTyped(val name: String) : SignUpScreenEvent
    class EmailTyped(val email: String) : SignUpScreenEvent
    class PasswordTyped(val password: String) : SignUpScreenEvent
    object SubmitRequest : SignUpScreenEvent
    object Retry : SignUpScreenEvent
}