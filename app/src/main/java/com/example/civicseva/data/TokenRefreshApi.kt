package com.example.civicseva.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RefreshRequest(val refreshToken: String)
data class RefreshResponse(val accessToken: String, val refreshToken: String)

interface TokenRefreshApi {
    @POST("api/auth/refresh-token")
    fun refreshAccessToken (
        @Body request: RefreshRequest
    ): Call<RefreshResponse>
}