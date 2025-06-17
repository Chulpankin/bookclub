package com.itis.bookclub.presentation.signup


import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.SignUpUseCase
import com.itis.bookclub.domain.utils.CrashlyticsService
import com.itis.bookclub.domain.utils.ScreenEvent
import com.itis.bookclub.presentation.signup.mvi.SignUpAction
import com.itis.bookclub.presentation.signup.mvi.SignUpEvent
import com.itis.bookclub.presentation.signup.mvi.SignUpState
import com.itis.bookclub.presentation.base.BaseViewModel
import com.itis.bookclub.util.AppException
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val appExceptionHandler: AppExceptionHandler,
    private val crashlyticsService: CrashlyticsService,
) : BaseViewModel<SignUpState, SignUpEvent, SignUpAction>(
    initialState = SignUpState()
) {


    override fun obtainEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignInClick -> {
                crashlyticsService.logScreenEvent(ScreenEvent.SIGN_IN, "from sign in click")
                _actionsFlow.tryEmit(SignUpAction.NavigateToSignIn)
            }
            is SignUpEvent.SignUpClick -> signUp()
            is SignUpEvent.NameChanged -> updateName(event.name)
            is SignUpEvent.EmailChanged -> updateEmail(event.email)
            is SignUpEvent.PasswordChanged -> updatePassword(event.password)
        }
    }

    private fun signUp() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            runSuspendCatching(appExceptionHandler) {
                signUpUseCase.invoke(_uiState.value.name, _uiState.value.email, _uiState.value.password)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
                crashlyticsService.logScreenEvent(ScreenEvent.SIGN_IN, "from sign up")
                _actionsFlow.emit(SignUpAction.NavigateToSignIn)
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isInvalidCredentials = it is AppException.AuthInvalidCredentialsException
                )
                _actionsFlow.emit(SignUpAction.ShowMessage(it.message.orEmpty()))
            }
        }
    }

    private fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name, isInvalidCredentials = false)
    }

    private fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, isInvalidCredentials = false)
    }

    private fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, isInvalidCredentials = false)
    }
}
