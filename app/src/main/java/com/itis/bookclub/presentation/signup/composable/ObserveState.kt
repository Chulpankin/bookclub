package com.itis.bookclub.presentation.signup.composable

import androidx.compose.runtime.Composable
import com.itis.bookclub.presentation.composable.LoadingScreen
import com.itis.bookclub.presentation.signup.mvi.SignUpState

@Composable
internal fun ObserveState(
    uiState: SignUpState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    SignUpContent(
        name = uiState.name,
        email = uiState.email,
        password = uiState.password,
        isInvalidCredentials = uiState.isInvalidCredentials,
        onNameChange = onNameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onSignUpClick = onSignUpClick,
        onSignInClick = onSignInClick)

    LoadingScreen(isLoading = uiState.isLoading)
}