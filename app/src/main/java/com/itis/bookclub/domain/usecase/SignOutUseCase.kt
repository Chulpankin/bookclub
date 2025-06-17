package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun invoke() {
        withContext(coroutineDispatcher) {
            userRepository.signOut()
        }
    }
}