package com.itis.bookclub.presentation.signin.composable

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.itis.bookclub.presentation.signin.SignInViewModel
import com.itis.bookclub.presentation.signin.mvi.SignInEvent

@Composable
internal fun SignInScreen(
    viewModel: SignInViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()


    ObserveActions(
        viewModel,
        snackbarHostState,
        onNavigateToSignUp,
        onNavigateToMain)

    ObserveState(
        uiState = uiState,
        onEmailChange = { viewModel.obtainEvent(SignInEvent.EmailChanged(it)) },
        onPasswordChange = { viewModel.obtainEvent(SignInEvent.PasswordChanged(it)) },
        onSignUpClick = { viewModel.obtainEvent(SignInEvent.SignUpClick) },
        onSignInClick = { viewModel.obtainEvent(SignInEvent.SignInClick) }
    )

    SnackbarHost(hostState = snackbarHostState)
}