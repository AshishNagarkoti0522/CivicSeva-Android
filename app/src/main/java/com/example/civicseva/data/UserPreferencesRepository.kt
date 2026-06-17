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
    private val TOKKEN_KEY = stringPreferencesKey("auth_token")

    val savedTokenFlow: Flow<String> = dataStore.data.map { it[TOKKEN_KEY] ?: "" }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKKEN_KEY] = token
        }
    }
}