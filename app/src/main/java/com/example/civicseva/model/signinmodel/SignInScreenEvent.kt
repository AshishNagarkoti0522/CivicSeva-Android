package com.example.civicseva.model.signinmodel

import com.example.civicseva.model.signupmodel.SignUpScreenEvent

sealed interface SignInScreenEvent {
    class EmailTyped(val email: String) : SignInScreenEvent
    class PasswordTyped(val password: String) : SignInScreenEvent
    object SubmitRequest : SignInScreenEvent
    object Retry : SignInScreenEvent
}