package com.example.civicseva.data

import com.example.civicseva.data.signup.SignupRequest
import com.example.civicseva.data.signup.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/signup")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ) : Response<SignupResponse>
}