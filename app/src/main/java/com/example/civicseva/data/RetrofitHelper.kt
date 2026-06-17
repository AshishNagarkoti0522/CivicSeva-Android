package com.example.civicseva.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitHelper {
    private const val BASE_URL = "https://0c9f7357-51f9-45d4-9c49-f03c23accc17.mock.pstmn.io/"

    // Json Converter Setup
    private val networkJson = Json { ignoreUnknownKeys = true }

    fun apiService(repository: UserPreferencesRepository): ApiService {
        val authInterceptor = AuthInterceptor(repository)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}