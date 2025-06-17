package com.itis.bookclub.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.itis.bookclub.domain.usecase.GetBooksUseCase
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

class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow(DEFAULT_QUERY)
    val query = _query.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val books: StateFlow<PagingData<BookUiModel>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getBooksUseCase.invoke(query).map { pagingData -> pagingData.map { it.toUiModel() } }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )

    fun setQuery(query: String) {
        _query.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "dictionary"
    }
}
