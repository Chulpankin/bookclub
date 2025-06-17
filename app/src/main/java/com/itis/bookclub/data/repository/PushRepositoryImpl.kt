package com.itis.bookclub.data.repository

import com.itis.bookclub.data.local.dao.PushDao
import com.itis.bookclub.data.util.toEntity
import com.itis.bookclub.domain.model.PushDomainModel
import com.itis.bookclub.domain.repository.PushRepository
import javax.inject.Inject

class PushRepositoryImpl @Inject constructor(
    private val pushDao: PushDao
) : PushRepository {

    override suspend fun addPush(push: PushDomainModel) {
        pushDao.insert(push.toEntity())
    }
}