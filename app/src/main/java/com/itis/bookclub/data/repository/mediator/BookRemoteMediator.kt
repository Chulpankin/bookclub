package com.itis.bookclub.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.local.entity.BookEntity
import com.itis.bookclub.data.local.entity.SearchQueryCacheEntity
import com.itis.bookclub.data.util.toEntity
import com.itis.bookclub.util.Constants
import com.itis.bookclub.util.Toaster

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val query: String,
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val cacheDao: SearchQueryCacheDao,
    private val toaster: Toaster,
) : RemoteMediator<Int, BookEntity>() {

    private var didNotify = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                (lastItem.indexInResponse / Constants.PAGE_LIMIT) + 1 + 1
            }
        }

        return try {
            val response = bookApi.getBooks(query, page, Constants.PAGE_LIMIT)
            val books = response?.bookList.orEmpty().mapIndexedNotNull { index, dto ->
                dto.toEntity(query, (page - 1) * Constants.PAGE_LIMIT + index)
            }
            if (loadType == LoadType.REFRESH) {
                bookDao.clearByQuery(query)
                val now = System.currentTimeMillis()
                val nextIndex = (cacheDao.getLatestIndex() ?: 0) + 1
                cacheDao.upsert(SearchQueryCacheEntity(query, now, nextIndex))
            }

            bookDao.insertAll(books)

            if (!didNotify) {
                if (loadType == LoadType.REFRESH) toaster.showFromApi() else toaster.showFromCache()
                didNotify = true
            }

            MediatorResult.Success(endOfPaginationReached = books.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
