package com.example.civicseva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.civicseva.data.RetrofitHelper
import com.example.civicseva.data.UserPreferencesRepository
import com.example.civicseva.data.dataStore
import com.example.civicseva.navigation.AppNavigation
import com.example.civicseva.ui.theme.CivicSevaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CivicSevaTheme {
                val context = LocalContext.current
                val repository = UserPreferencesRepository(context.dataStore)

                val apiService = RetrofitHelper.apiService(repository)

                AppNavigation(apiService = apiService, repository = repository)
            }
        }
    }
}