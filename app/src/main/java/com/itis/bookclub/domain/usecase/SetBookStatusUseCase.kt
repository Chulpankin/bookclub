package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetBookStatusUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(userId: String, book: BookDetailsDomainModel, status: BookStatus) {
        withContext(coroutineDispatcher) {
            bookRepository.setBookStatus(userId, book, status)
        }
    }
}