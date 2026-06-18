package com.example.civicseva.data

import com.example.civicseva.data.signin.SignInRequest
import com.example.civicseva.data.signin.SignInResponse
import com.example.civicseva.data.signup.SignUpRequest
import com.example.civicseva.data.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //Sign Up
    @POST("api/auth/signup")
    suspend fun signup(
        @Body signupRequest: SignUpRequest
    ) : Response<SignUpResponse>

    //Sign In
    @POST("api/auth/signin")
    suspend fun signin(
        @Body signinRequest: SignInRequest
    ) : Response<SignInResponse>
}