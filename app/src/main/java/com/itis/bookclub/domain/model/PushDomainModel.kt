package com.itis.bookclub.domain.model

class PushDomainModel(
    val id: Long = 0L,
    val category: String,
    val payload: String,
    val timestamp: Long
)