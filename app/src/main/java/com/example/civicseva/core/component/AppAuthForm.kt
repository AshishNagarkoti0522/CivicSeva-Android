package com.example.civicseva.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.civicseva.R

@Composable
fun AppAuthForm(
    modifier: Modifier = Modifier,
    isSignIn: Boolean,
    username: String = "",
    email: String,
    password: String,
    emailErrorMessage: String?,
    passwordErrorMessage: String?,
    usernameErrorMessage: String? = null,
    usernameChanged: (String) -> Unit = {},
    passwordChanged: (String) -> Unit = {},
    emailChanged: (String) -> Unit = {},
    toggleAuthMode: () -> Unit = {},
    submitForm: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // username
        if (!isSignIn) {
            AppTextField(
                value = username,
                onValueChange = { usernameChanged(it) },
                label = stringResource(R.string.username),
                placeholder = stringResource(R.string.username_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                errorMessage = usernameErrorMessage
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // email
        AppTextField(
            value = email,
            onValueChange = { emailChanged(it) },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.email_placeholder),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            errorMessage = emailErrorMessage
        )

        Spacer(modifier = Modifier.height(16.dp))

        // password
        AppTextField(
            value = password,
            onValueChange = { passwordChanged(it) },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password_placeholder),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
            },
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(onGo = { submitForm() }),
            errorMessage = passwordErrorMessage
        )

        Spacer(modifier = Modifier.height(16.dp))

        // toggle auth mode
        AppText(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { toggleAuthMode() },
            text = if (isSignIn) stringResource(R.string.don_t_have_an_account_sign_up) else stringResource(R.string.already_have_an_account_sign_in),
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun AuthFormPreview() {
    AppAuthForm(
        isSignIn = false,
        email = "ashishn0522@gmail.com",
        emailErrorMessage = null,
        password = "StrongPass@123",
        passwordErrorMessage = null
    )
}