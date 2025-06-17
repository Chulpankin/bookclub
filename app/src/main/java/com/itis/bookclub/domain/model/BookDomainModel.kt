package com.itis.bookclub.domain.model

class BookDomainModel(
    val authorIdList: List<String>,
    val bookId: String,
    val authorNameList: List<String>,
    val title: String,
    val publishedYear: Int,
    val coverUrl: String,
    val status: BookStatus? = null
)