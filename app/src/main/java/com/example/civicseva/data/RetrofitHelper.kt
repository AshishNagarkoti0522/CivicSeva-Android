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
        val plainOkHttpClient = OkHttpClient.Builder().build()

        val tokenApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(plainOkHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TokenRefreshApi::class.java)

        val authenticator = TokenAuthenticator(repository = repository, api = tokenApi)
        val authInterceptor = AuthInterceptor(repository)

        val mainOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mainOkHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}