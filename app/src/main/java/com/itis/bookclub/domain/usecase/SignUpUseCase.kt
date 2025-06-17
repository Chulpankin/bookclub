package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(name: String, email: String, password: String) {
        withContext(coroutineDispatcher) {
            userRepository.signUp(name, email, password)
        }
    }
}