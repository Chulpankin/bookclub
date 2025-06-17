package com.itis.bookclub.data.repository.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.model.BookStatus
import kotlinx.coroutines.tasks.await

class FirebaseBooksPagingSource(
    private val firestore: FirebaseFirestore,
    private val userId: String,
    private val statusFilter: BookStatus?,
    private val pageSize: Int = 20
) : PagingSource<DocumentSnapshot, BookDomainModel>() {

    override suspend fun load(
        params: LoadParams<DocumentSnapshot>
    ): LoadResult<DocumentSnapshot, BookDomainModel> {
        return try {
            var query = firestore.collection("user_books")
                .document(userId)
                .collection("books")
                .orderBy("bookId")
                .limit(pageSize.toLong())

            if (statusFilter != null) {
                query = query.whereEqualTo("status", statusFilter.name)
            }

            params.key?.let {
                query = query.startAfter(it)
            }

            val snapshot = query.get().await()
            val documents = snapshot.documents

            val books = documents.mapNotNull { doc ->
                try {
                    val data = doc.data ?: return@mapNotNull null
                    BookDomainModel(
                        authorIdList = emptyList(),
                        bookId = data["bookId"] as? String ?: return@mapNotNull null,
                        authorNameList = emptyList(),
                        title = data["title"] as? String ?: "",
                        coverUrl = data["coverUrl"] as? String ?: "",
                        status = (data["status"] as? String)?.let { BookStatus.valueOf(it) },
                        publishedYear = 0,
                    )
                } catch (e: Exception) {
                    null
                }
            }

            val nextKey = if (documents.isNotEmpty()) documents.last() else null

            LoadResult.Page(
                data = books,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<DocumentSnapshot, BookDomainModel>
    ): DocumentSnapshot? {
        return null
    }
}
