package com.itis.bookclub.data.di

import com.itis.bookclub.data.repository.BookRepositoryImpl
import com.itis.bookclub.data.repository.PushRepositoryImpl
import com.itis.bookclub.data.repository.UserRepositoryImpl
import com.itis.bookclub.domain.repository.BookRepository
import com.itis.bookclub.domain.repository.PushRepository
import com.itis.bookclub.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindBookRepository(impl: BookRepositoryImpl): BookRepository

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindPushRepository(impl: PushRepositoryImpl): PushRepository

}