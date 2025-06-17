package com.itis.bookclub.presentation.signup.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.itis.bookclub.presentation.signup.SignUpViewModel
import com.itis.bookclub.presentation.signup.mvi.SignUpAction

@Composable
internal fun ObserveActions(
    viewModel: SignUpViewModel,
    snackbarHostState: SnackbarHostState,
    onNavigateToSignIn: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.actionsFlow.collect { action ->
            when (action) {
                is SignUpAction.ShowMessage ->
                    snackbarHostState.showSnackbar(message = action.message)

                SignUpAction.NavigateToSignIn ->
                    onNavigateToSignIn()
            }
        }
    }
}