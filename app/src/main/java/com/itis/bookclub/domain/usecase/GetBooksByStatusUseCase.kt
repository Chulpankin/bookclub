package com.itis.bookclub.domain.usecase

import androidx.paging.PagingData
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksByStatusUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {

    operator fun invoke(userId: String, status: BookStatus?): Flow<PagingData<BookDomainModel>> =
        bookRepository.getBooksByStatus(userId, status)
}
