package com.itis.bookclub.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State, Event, Action>(
    initialState: State
) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialState)

    protected val _actionsFlow = MutableSharedFlow<Action>(
        extraBufferCapacity = 1
    )

    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    val actionsFlow: SharedFlow<Action>
        get() = _actionsFlow.asSharedFlow()

    open fun obtainEvent(event: Event) {}
}