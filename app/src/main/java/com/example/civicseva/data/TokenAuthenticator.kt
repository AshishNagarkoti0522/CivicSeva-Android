package com.example.civicseva.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val api: TokenRefreshApi,
    private val repository: UserPreferencesRepository
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // current refresh tokken from data store
        val refreshToken = runBlocking { repository.savedRefreshTokenFlow.first() }

        // if no refresh token, redirect to signup
        if (refreshToken.isEmpty()) { return null }

        // SYNCHRONOUS API call
        val refreshResponse = api.refreshAccessToken(RefreshRequest(refreshToken)).execute()

        return if(refreshResponse.isSuccessful) {
            // 3. Naye tokens nikal lo
            val newAccessToken = refreshResponse.body()?.accessToken ?: return null
            val newRefreshToken = refreshResponse.body()?.refreshToken ?: return null

            // 4. Naye tokens ko DataStore me save kar do
            runBlocking { repository.saveTokens(newAccessToken, newRefreshToken) }

            // 5. Jo request 401 par fail hui thi, usme naya token lagao aur wapas chala do!
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } else {
            runBlocking { repository.clearTokens() }
            null
        }
    }
}