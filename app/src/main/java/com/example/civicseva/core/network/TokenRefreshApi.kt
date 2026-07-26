package com.example.civicseva.core.network

import com.example.civicseva.data.tokenrefresh.RefreshRequest
import com.example.civicseva.data.tokenrefresh.RefreshResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenRefreshApi {
    @POST("auth/refresh")
    fun refreshAccessToken (
        @Body request: RefreshRequest
    ): Call<RefreshResponse>
}