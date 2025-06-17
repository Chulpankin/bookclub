package com.itis.bookclub.presentation.signup.mvi

sealed interface SignUpAction {
    data class ShowMessage(val message: String) : SignUpAction
    data object NavigateToSignIn : SignUpAction
}