@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.civicseva.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.civicseva.R
import com.example.civicseva.component.AppButton
import com.example.civicseva.component.AppLoader
import com.example.civicseva.component.AppScaffold
import com.example.civicseva.component.AppText
import com.example.civicseva.component.AppTextField
import com.example.civicseva.component.AppTopBar
import com.example.civicseva.model.UiEvent
import com.example.civicseva.model.signupmodel.SignUpScreenEvent
import com.example.civicseva.model.signupmodel.SignUpUiState
import com.example.civicseva.viewmodel.SignUpVM

@Composable
fun SignUpScreen(
    vm: SignUpVM = viewModel(),
    onSignInClick: () -> Unit = {},
    onAuthSuccess: () -> Unit = {}
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.NavigateToHome -> {
                    onAuthSuccess()
                }
            }
        }
    }

    AppScaffold(
        topBarTitle = { isScrollable ->
            AppTopBar(
                title = stringResource(R.string.sign_up),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                scrollBehavior = isScrollable
            )
        }
    ) { innerPadding ->

        var passwordVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {

            when (uiState) {
                is SignUpUiState.Idle -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppTextField(
                            value = vm.screenState.name.value,
                            onValueChange = { vm.onEvent(SignUpScreenEvent.NameTyped(it)) },
                            label = stringResource(R.string.name),
                            placeholder = stringResource(R.string.enter_your_full_name),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            errorMessage = vm.screenState.name.errorMessage
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AppTextField(
                            value = vm.screenState.email.value,
                            onValueChange = { vm.onEvent(SignUpScreenEvent.EmailTyped(it)) },
                            label = stringResource(R.string.email),
                            placeholder = stringResource(R.string.enter_your_email),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Icon"
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            errorMessage = vm.screenState.email.errorMessage
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AppTextField(
                            value = vm.screenState.password.value,
                            onValueChange = { vm.onEvent(SignUpScreenEvent.PasswordTyped(it)) },
                            label = stringResource(R.string.password),
                            placeholder = stringResource(R.string.enter_your_password),
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                            },
                            trailingIcon = {
                                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                val description = if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            errorMessage = vm.screenState.password.errorMessage
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AppText(
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { onSignInClick() },
                            text = stringResource(R.string.already_have_an_account_sign_in),
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(R.string.sign_up),
                        onClick = { vm.onEvent(SignUpScreenEvent.SubmitRequest) }
                    )
                }
                is SignUpUiState.Loading -> {
                    AppLoader()
                }
                is SignUpUiState.Success -> {
                    val message = (uiState as SignUpUiState.Success).data.message
                    AppText(
                        text = message
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SignUpScreen()
}