package com.itis.bookclub.domain.repository

interface UserRepository {

    suspend fun signUp(name: String, email: String, password: String)

    suspend fun signIn(email: String, password: String)

    suspend fun signOut()

    fun getCurrentUserId(): String

    fun isUserAuthorized(): Boolean

    suspend fun getUserCredentials(userId: String): String
}