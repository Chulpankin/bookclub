package com.itis.bookclub.presentation.signup.composable

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.itis.bookclub.presentation.signup.SignUpViewModel
import com.itis.bookclub.presentation.signup.mvi.SignUpEvent

@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToSignIn: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()


    ObserveActions(
        viewModel,
        snackbarHostState,
        onNavigateToSignIn
    )

    ObserveState(
        uiState = uiState,
        onNameChange = { viewModel.obtainEvent(SignUpEvent.NameChanged(it)) },
        onEmailChange = { viewModel.obtainEvent(SignUpEvent.EmailChanged(it)) },
        onPasswordChange = { viewModel.obtainEvent(SignUpEvent.PasswordChanged(it)) },
        onSignUpClick = { viewModel.obtainEvent(SignUpEvent.SignUpClick) },
        onSignInClick = { viewModel.obtainEvent(SignUpEvent.SignInClick) }
    )

    SnackbarHost(hostState = snackbarHostState)
}