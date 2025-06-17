package com.itis.bookclub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BookListResponse(
    @SerialName("docs")
    val bookList: List<BookResponse>?
)