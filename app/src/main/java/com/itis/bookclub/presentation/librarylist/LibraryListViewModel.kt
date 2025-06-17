package com.itis.bookclub.presentation.librarylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.usecase.GetBooksByStatusUseCase
import com.itis.bookclub.domain.usecase.GetUserIdUseCase
import com.itis.bookclub.presentation.model.BookUiModel
import com.itis.bookclub.presentation.utils.toUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LibraryListViewModel @Inject constructor(
    private val getBooksByStatusUseCase: GetBooksByStatusUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
) : ViewModel() {

    private val userId = getUserIdUseCase.invoke()

    private val _status = MutableStateFlow<BookStatus?>(null)
    val status = _status.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val books: StateFlow<PagingData<BookUiModel>> = _status
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { status ->
            getBooksByStatusUseCase
                .invoke(userId, status)
                .map { pagingData -> pagingData.map { it.toUiModel() } }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )

    fun setStatusFilter(status: BookStatus?) {
        _status.value = status
    }
}

