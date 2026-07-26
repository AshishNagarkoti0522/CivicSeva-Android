package com.example.civicseva.data.repository

import com.example.civicseva.core.network.ApiService
import com.example.civicseva.core.network.NetworkResult
import com.example.civicseva.data.signin.SignInRequest
import com.example.civicseva.data.signin.SignInResponse
import com.example.civicseva.data.signup.SignUpRequest
import com.example.civicseva.data.signup.SignUpResponse
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val userPrefs: UserPreferencesRepository
): AuthRepository {
    override suspend fun signIn(request: SignInRequest): NetworkResult<SignInResponse> {
        return try {
            val response = apiService.signin(request)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                val accessToken = data.data.tokens.accessToken
                val refreshToken = data.data.tokens.refreshToken

                userPrefs.saveTokens(accessToken, refreshToken)

                NetworkResult.Success(data)
            } else {
                val errorMsg = when (response.code()) {
                    401 -> "Your session has expired. Please sign in again."
                    429 -> "Too many requests. Please try again later."
                    500 -> "Server error. Please try again later."
                    else -> response.errorBody()?.string() ?: "Something went wrong"
                }
                NetworkResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            NetworkResult.Error("Please check your internet connection")
        }
    }

    override suspend fun signUp(request: SignUpRequest): NetworkResult<SignUpResponse> {
        return try {
            val response = apiService.signup(request)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                val accessToken = data.data.tokens.accessToken
                val refreshToken = data.data.tokens.refreshToken

                userPrefs.saveTokens(accessToken, refreshToken)
                NetworkResult.Success(data)
            } else {
                val errorMsg = when (response.code()) {
                    400 -> "Invalid request. Please try again"
                    409 -> "User already exists"
                    500 -> "Server error. Please try again later."
                    else -> response.errorBody()?.string() ?: "Something went wrong"
                }
                NetworkResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            NetworkResult.Error("Please check your internet connection")
        }
    }
}