package com.itis.bookclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.bookclub.data.local.entity.SearchQueryCacheEntity

@Dao
interface SearchQueryCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cache: SearchQueryCacheEntity)

    @Query("SELECT * FROM search_query_cache WHERE `query` = :query LIMIT 1")
    suspend fun getCache(query: String): SearchQueryCacheEntity?

    @Query("SELECT COUNT(*) FROM search_query_cache WHERE `index` > :lastIndex")
    suspend fun countQueriesAfter(lastIndex: Int): Int

    @Query("SELECT MAX(`index`) FROM search_query_cache")
    suspend fun getLatestIndex(): Int?
}