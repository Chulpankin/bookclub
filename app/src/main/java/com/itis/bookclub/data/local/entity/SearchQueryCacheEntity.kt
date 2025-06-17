package com.itis.bookclub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query_cache")
data class SearchQueryCacheEntity(
    @PrimaryKey val query: String,
    val lastRequestedAt: Long,
    val index: Int
)
