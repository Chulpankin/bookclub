package com.itis.bookclub.presentation.signin.mvi

sealed interface SignInAction {
    data class ShowMessage(val message: String) : SignInAction
    data object NavigateToSignUp : SignInAction
    data object NavigateToMain : SignInAction
}