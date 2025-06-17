package com.itis.bookclub.domain.repository

import androidx.paging.PagingData
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.model.BookStatus
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(query: String) : Flow<PagingData<BookDomainModel>>

    fun getBooksByStatus(userId: String, status: BookStatus?): Flow<PagingData<BookDomainModel>>

    suspend fun getBookById(bookId: String, userId: String) : BookDetailsDomainModel

    suspend fun setBookStatus(
        userId: String,
        book: BookDetailsDomainModel,
        status: BookStatus
    )
}
