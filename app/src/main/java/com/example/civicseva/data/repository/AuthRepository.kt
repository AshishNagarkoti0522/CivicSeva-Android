package com.example.civicseva.data.repository

import com.example.civicseva.core.network.NetworkResult
import com.example.civicseva.data.signin.SignInRequest
import com.example.civicseva.data.signin.SignInResponse
import com.example.civicseva.data.signup.SignUpRequest
import com.example.civicseva.data.signup.SignUpResponse

interface AuthRepository {
    suspend fun signIn(request: SignInRequest): NetworkResult<SignInResponse>
    suspend fun signUp(request: SignUpRequest): NetworkResult<SignUpResponse>
}