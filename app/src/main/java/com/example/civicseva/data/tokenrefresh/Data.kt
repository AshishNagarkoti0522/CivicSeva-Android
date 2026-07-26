package com.example.civicseva.data.tokenrefresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("tokens")
    val tokens: Tokens
)