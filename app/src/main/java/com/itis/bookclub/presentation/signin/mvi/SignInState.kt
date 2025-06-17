package com.itis.bookclub.presentation.signin.mvi

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isInvalidCredentials: Boolean = false,
    val isLoading: Boolean = false,
)