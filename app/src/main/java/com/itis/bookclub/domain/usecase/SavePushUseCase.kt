package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.model.PushDomainModel
import com.itis.bookclub.domain.repository.PushRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePushUseCase @Inject constructor(
    private val repository: PushRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(push: PushDomainModel) =
        withContext(coroutineDispatcher) {
            repository.addPush(push)
        }
}