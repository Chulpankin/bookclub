package com.itis.bookclub.data.util

import BookDetailsResponse
import com.itis.bookclub.data.local.entity.BookEntity
import com.itis.bookclub.data.local.entity.PushEntity
import com.itis.bookclub.data.model.BookResponse
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.domain.model.PushDomainModel

fun BookDetailsResponse.toDomainModel() =
    BookDetailsDomainModel(
        bookId = requireNotNull(key),
        title = requireNotNull(title),
        coverUrl = covers[0].toString().toOpenLibraryCoverUrl(),
        description = requireNotNull(description?.value),
    )

fun BookResponse.toEntity(query: String = "", indexInResponse: Int): BookEntity? {
    val id = key?.removePrefix("/works/") ?: return null
    val titleSafe = title ?: return null
    val coverSafe = coverId?.toOpenLibraryCoverUrl() ?: ""
    val authorNames = authorNameList.filterNotNull().joinToString(", ")
    val authorIds = authorKeyList.filterNotNull().joinToString(", ")

    return BookEntity(
        bookId = id,
        title = titleSafe,
        authorNames = authorNames,
        authorIds = authorIds,
        publishedYear = firstPublishedYear,
        coverUrl = coverSafe,
        query = query,
        indexInResponse = indexInResponse
    )
}

fun BookDetailsDomainModel.toFirebaseDoc(status: BookStatus): Map<String, Any> =
    mapOf(
        "bookId" to bookId,
        "title" to title,
        "coverUrl" to coverUrl,
        "status" to status.name
    )

fun BookEntity.toDomainModel() =
    BookDomainModel(
        authorIdList = authorIds.split(", ").filter { it.isNotBlank() },
        bookId = bookId,
        authorNameList = authorNames.split(", ").filter { it.isNotBlank() },
        title = title,
        publishedYear = publishedYear,
        coverUrl = coverUrl
    )


fun PushDomainModel.toEntity() =
    PushEntity(
        id = this.id,
        category = this.category,
        payload = this.payload,
        timestamp = this.timestamp
    )

fun String.toOpenLibraryCoverUrl() = "https://covers.openlibrary.org/b/id/$this.jpg"