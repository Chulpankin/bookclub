package com.itis.bookclub.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.usecase.GetBookDetailsUseCase
import com.itis.bookclub.domain.usecase.GetUserIdUseCase
import com.itis.bookclub.domain.usecase.SetBookStatusUseCase
import com.itis.bookclub.presentation.model.BookDetailsUiModel
import com.itis.bookclub.presentation.utils.toDomainModel
import com.itis.bookclub.presentation.utils.toUiModel
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    @Assisted private val bookId: String,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val setBookStatusUseCase: SetBookStatusUseCase,
    private val exceptionHandler: AppExceptionHandler,
) : ViewModel() {

    private var currentUserId: String = getUserIdUseCase.invoke()

    private val _book = MutableStateFlow<BookDetailsUiModel?>(null)
    val book = _book.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _status = MutableStateFlow<BookStatus?>(null)
    val status = _status.asStateFlow()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _book.value = null
            _isLoading.value = true
            _isError.value = false

            runSuspendCatching(exceptionHandler) {
                getBookDetailsUseCase.invoke(bookId, currentUserId)
            }.onSuccess {
                _book.value = it.toUiModel()
                _status.value = it.status
                _isLoading.value = false
            }.onFailure {
                _isError.value = true
            }
        }
    }

    fun setStatus(status: BookStatus) {
        viewModelScope.launch {
            runSuspendCatching(exceptionHandler) {
                _book.value?.let {
                    setBookStatusUseCase.invoke(
                        userId = currentUserId,
                        book = it.toDomainModel(bookId),
                        status = status
                    )
                }
            }.onSuccess {
                _status.value = status
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted bookId: String): BookDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            bookId: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(bookId) as T
            }
        }
    }
}