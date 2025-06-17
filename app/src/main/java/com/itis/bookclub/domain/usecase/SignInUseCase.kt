package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(email: String, password: String) {
        withContext(coroutineDispatcher) {
            userRepository.signIn(email, password)
        }
    }
}