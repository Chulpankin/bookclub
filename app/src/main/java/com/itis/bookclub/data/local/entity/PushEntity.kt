package com.itis.bookclub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pushes")
data class PushEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val category: String,
    val payload: String,
    val timestamp: Long
)