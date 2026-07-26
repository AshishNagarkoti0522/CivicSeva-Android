package com.example.civicseva.data.signup


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("access_token_expires_in")
    val accessTokenExpiresIn: Int,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int
)