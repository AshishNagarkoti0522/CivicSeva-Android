@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.civicseva.features.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.civicseva.R
import com.example.civicseva.core.component.AppAuthForm
import com.example.civicseva.core.component.AppButton
import com.example.civicseva.core.component.AppLoader
import com.example.civicseva.core.component.AppScaffold
import com.example.civicseva.core.component.AppTopBar
import com.example.civicseva.core.utils.SideEffects

@Composable
fun SignUpScreen(
    vm: SignUpVM = hiltViewModel(),
    onSignInClick: () -> Unit = {},
    onAuthSuccess: () -> Unit = {}
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val state = vm.state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.uiEvent.collect { event ->
            when(event) {
                is SideEffects.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    AppScaffold(
        topBar = { isScrollable ->
            AppTopBar(
                title = stringResource(R.string.sign_up),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                scrollBehavior = isScrollable
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {

            when (uiState) {
                is SignUpContract.UiState.Idle -> {
                    AppAuthForm(
                        isSignIn = false,
                        username = state.username.value,
                        usernameChanged = { vm.onEvent(SignUpContract.Event.NameTyped(it)) },
                        usernameErrorMessage = state.username.errorMessage,
                        email = state.email.value,
                        emailChanged = { vm.onEvent(SignUpContract.Event.EmailTyped(it)) },
                        emailErrorMessage = state.email.errorMessage,
                        password = state.password.value,
                        passwordChanged = { vm.onEvent(SignUpContract.Event.PasswordTyped(it)) },
                        passwordErrorMessage = state.password.errorMessage,
                        toggleAuthMode = { onSignInClick() },
                        submitForm = { vm.onEvent(SignUpContract.Event.SubmitRequest) }
                    )

                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(R.string.sign_up),
                        onClick = { vm.onEvent(SignUpContract.Event.SubmitRequest) }
                    )
                }
                is SignUpContract.UiState.Loading -> {
                    AppLoader()
                }
                is SignUpContract.UiState.Success -> {
                    onAuthSuccess()
                }
            }
        }
    }
}