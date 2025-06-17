package com.itis.bookclub.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.repository.mediator.BookRemoteMediator
import com.itis.bookclub.data.repository.mediator.FirebaseBooksPagingSource
import com.itis.bookclub.data.util.toDomainModel
import com.itis.bookclub.data.util.toFirebaseDoc
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.repository.BookRepository
import com.itis.bookclub.util.Constants
import com.itis.bookclub.util.Toaster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val cacheDao: SearchQueryCacheDao,
    private val firestore: FirebaseFirestore,
    private val toaster: Toaster,
) : BookRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getBooks(query: String): Flow<PagingData<BookDomainModel>> {
        val pagingSourceFactory = { bookDao.pagingSource(query) }

        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_LIMIT,
                prefetchDistance = Constants.PAGE_LIMIT / 2
            ),
            remoteMediator = BookRemoteMediator(
                query = query,
                bookApi = bookApi,
                bookDao = bookDao,
                cacheDao = cacheDao,
                toaster = toaster,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    override fun getBooksByStatus(
        userId: String,
        status: BookStatus?
    ): Flow<PagingData<BookDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_LIMIT,
                prefetchDistance = Constants.PAGE_LIMIT / 2
            ),
            pagingSourceFactory = {
                FirebaseBooksPagingSource(
                    firestore = firestore,
                    userId = userId,
                    statusFilter = status
                )
            }
        ).flow
    }

    override suspend fun getBookById(bookId: String, userId: String): BookDetailsDomainModel {
        val response = requireNotNull(bookApi.getBookById(bookId)).toDomainModel()

        val bookStatus = getBookStatus(userId, bookId)

        return response.copy(status = bookStatus)
    }



    override suspend fun setBookStatus(
        userId: String,
        book: BookDetailsDomainModel,
        status: BookStatus
    ) {
        val doc = firestore.collection("user_books").document(userId)
            .collection("books").document(book.bookId)

        doc.set(book.toFirebaseDoc(status)).await()
    }

    private suspend fun getBookStatus(userId: String, bookId: String): BookStatus? {
        val snapshot = firestore.collection("user_books").document(userId)
            .collection("books").document(bookId).get().await()

        return snapshot.getString("status")?.let { BookStatus.valueOf(it) }
    }
}
