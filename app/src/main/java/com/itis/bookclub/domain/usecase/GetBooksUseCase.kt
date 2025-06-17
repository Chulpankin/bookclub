package com.itis.bookclub.domain.usecase

import androidx.paging.PagingData
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {

    operator fun invoke(query: String) : Flow<PagingData<BookDomainModel>> {
        return bookRepository.getBooks(query = query)
    }
}