package com.itis.bookclub.presentation.signup.mvi

sealed interface SignUpEvent {
    data object SignInClick : SignUpEvent
    data object SignUpClick : SignUpEvent
    data class NameChanged(val name: String) : SignUpEvent
    data class EmailChanged(val email: String) : SignUpEvent
    data class PasswordChanged(val password: String) : SignUpEvent
}