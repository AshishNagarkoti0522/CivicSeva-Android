@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.civicseva.screen

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.civicseva.model.signupmodel.SignUpScreenEvent
import com.example.civicseva.model.signupmodel.SignupUistate
import com.example.civicseva.ui.theme.CivicSevaTheme
import com.example.civicseva.viewmodel.SignupVM

@Composable
fun SignUpScreen(
    vm: SignupVM = viewModel()
) {
    val uiState by vm.uiState.collectAsState()

    AppScaffold(
        topBarTitle = { isScrollable ->
            AppTopBar(
                title = "Sign Up",
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
                is SignupUistate.Idle -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppTextField(
                            value = vm.screenState.name.value,
                            onValueChange = { vm.onEvent(SignUpScreenEvent.NameTyped(it)) },
                            label = "Name",
                            placeholder = "Enter full name",
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
                            label = "Email",
                            placeholder = "Enter your email",
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
                            label = "Password",
                            placeholder = "Enter your password",
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
                    }

                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(R.string.sign_up),
                        onClick = { vm.onEvent(SignUpScreenEvent.SubmitRequest) }
                    )
                }
                is SignupUistate.Loading -> {
                    AppLoader()
                }
                is SignupUistate.Success -> {
                    val message = (uiState as SignupUistate.Success).data.message
                    AppText(
                        text = message
                    )
                }
                is SignupUistate.Error -> {
                    val message = (uiState as SignupUistate.Error).message

                    AppText(
                        text = message
                    )

                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(R.string.retry),
                        onClick = { vm.onEvent(SignUpScreenEvent.Retry) }
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