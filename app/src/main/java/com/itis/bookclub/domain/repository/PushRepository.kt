package com.itis.bookclub.domain.repository

import com.itis.bookclub.domain.model.PushDomainModel

interface PushRepository {
    suspend fun addPush(push: PushDomainModel)
}

