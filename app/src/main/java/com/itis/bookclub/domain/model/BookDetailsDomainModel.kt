package com.itis.bookclub.domain.model

data class BookDetailsDomainModel(
    val bookId: String,
    val title: String,
    val coverUrl: String,
    val description: String,
    val status: BookStatus? = null
)

enum class BookStatus {
    PLANNED, READING, FINISHED
}
