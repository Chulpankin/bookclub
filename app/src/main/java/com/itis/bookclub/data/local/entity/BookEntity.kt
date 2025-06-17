package com.itis.bookclub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val bookId: String,
    val title: String,
    val authorNames: String,
    val authorIds: String,
    val publishedYear: Int,
    val coverUrl: String,
    val query: String,
    val indexInResponse: Int
)
