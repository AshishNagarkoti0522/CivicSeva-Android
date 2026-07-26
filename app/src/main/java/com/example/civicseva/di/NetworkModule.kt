package com.example.civicseva.di

import com.example.civicseva.core.network.ApiService
import com.example.civicseva.core.network.AuthInterceptor
import com.example.civicseva.core.network.TokenAuthenticator
import com.example.civicseva.core.network.TokenRefreshApi
import com.example.civicseva.data.repository.UserPreferencesRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlainOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://991bbad9-95e1-4303-ab67-df256164b0ab.mock.pstmn.io/"
    private val networkJson = Json {ignoreUnknownKeys = true}

    @Provides
    @Singleton
    fun provideAuthInterceptor(repository: UserPreferencesRepository): AuthInterceptor {
        return AuthInterceptor(repository)
    }

    @Provides
    @Singleton
    @PlainOkHttpClient
    fun providePlainOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenRefreshApi(@PlainOkHttpClient okHttpClient: OkHttpClient): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TokenRefreshApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        repository: UserPreferencesRepository,
        tokenRefreshApi: TokenRefreshApi
    ): TokenAuthenticator {
        return TokenAuthenticator(repository = repository, api = tokenRefreshApi)
    }

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideMainOkHttpClient(
        authInterceptor: AuthInterceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainApiService(@AuthOkHttpClient okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}