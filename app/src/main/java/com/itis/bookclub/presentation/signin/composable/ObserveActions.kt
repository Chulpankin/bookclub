package com.itis.bookclub.presentation.signin.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.itis.bookclub.presentation.signin.SignInViewModel
import com.itis.bookclub.presentation.signin.mvi.SignInAction

@Composable
internal fun ObserveActions(
    viewModel: SignInViewModel,
    snackbarHostState: SnackbarHostState,
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.actionsFlow.collect { action ->
            when (action) {
                is SignInAction.ShowMessage ->
                    snackbarHostState.showSnackbar(message = action.message)

                SignInAction.NavigateToSignUp ->
                    onNavigateToSignUp()

                SignInAction.NavigateToMain ->
                    onNavigateToMain()
            }
        }
    }
}