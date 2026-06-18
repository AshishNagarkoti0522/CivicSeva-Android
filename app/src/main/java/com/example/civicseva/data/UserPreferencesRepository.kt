package com.example.civicseva.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val ACCESS_TOKKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKKEN = stringPreferencesKey("refresh_token")

    val savedAccessTokenFlow: Flow<String> = dataStore.data.map { it[ACCESS_TOKKEN] ?: "" }
    val savedRefreshTokenFlow: Flow<String> = dataStore.data.map { it[REFRESH_TOKKEN] ?: "" }

    suspend fun saveTokens(access: String, refresh: String) {
        dataStore.edit {
            it[ACCESS_TOKKEN] = access
            it[REFRESH_TOKKEN] = refresh
        }
    }

    suspend fun clearTokens() {
        dataStore.edit{
            it.remove(ACCESS_TOKKEN)
            it.remove(REFRESH_TOKKEN)
        }
    }
}