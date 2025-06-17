package com.itis.bookclub.data.api

import BookDetailsResponse
import com.itis.bookclub.data.model.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("search.json")
    suspend fun getBooks(
        @Query(value = "q") query: String,
        @Query(value = "page") page: Int,
        @Query(value = "limit") limit: Int
    ): BookListResponse?

    @GET("works/{id}.json")
    suspend fun getBookById(
        @Path(value = "id") id: String
    ): BookDetailsResponse?
}