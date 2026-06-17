package com.example.civicseva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.civicseva.data.RetrofitHelper
import com.example.civicseva.data.UserPreferencesRepository
import com.example.civicseva.data.dataStore
import com.example.civicseva.screen.SignUpScreen
import com.example.civicseva.ui.theme.CivicSevaTheme
import com.example.civicseva.viewmodel.SignupVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CivicSevaTheme {
                val context = LocalContext.current
                val repository = UserPreferencesRepository(context.dataStore)

                val apiService = RetrofitHelper.apiService(repository)

                val factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return SignupVM(
                            apiService = apiService,
                            repository = repository
                        ) as T
                    }
                }

                val vm: SignupVM = viewModel(factory = factory)

                SignUpScreen(vm = vm)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CivicSevaTheme { SignUpScreen() }
}