package com.itis.bookclub.presentation.model

import com.itis.bookclub.domain.model.BookStatus

data class BookUiModel(
    val id: String,
    val authorNames: String,
    val title: String,
    val publishedYear: String,
    val coverUrl: String,
    val status: BookStatus? = null
)