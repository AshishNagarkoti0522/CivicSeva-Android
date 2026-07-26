package com.example.civicseva.core.network

import com.example.civicseva.data.signin.SignInRequest
import com.example.civicseva.data.signin.SignInResponse
import com.example.civicseva.data.signup.SignUpRequest
import com.example.civicseva.data.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //Sign Up
    @POST("auth/register")
    suspend fun signup(
        @Body signupRequest: SignUpRequest
    ) : Response<SignUpResponse>

    //Sign In
    @POST("auth/login")
    suspend fun signin(
        @Body signinRequest: SignInRequest
    ) : Response<SignInResponse>
}