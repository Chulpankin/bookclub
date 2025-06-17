package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.repository.UserRepository
import javax.inject.Inject

class IsUserAuthorizedUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    operator fun invoke(): Boolean = userRepository.isUserAuthorized()
}