package com.itis.bookclub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BookResponse(
    @SerialName("author_key")
    val authorKeyList: List<String?>,
    @SerialName("author_name")
    val authorNameList: List<String?>,
    @SerialName("title")
    val title: String?,
    @SerialName("cover_i")
    val coverId: String?,
    @SerialName("first_publish_year")
    val firstPublishedYear: Int = 0,
    @SerialName("key")
    val key: String?,
)