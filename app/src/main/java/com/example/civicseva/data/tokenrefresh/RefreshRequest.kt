package com.example.civicseva.data.tokenrefresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)