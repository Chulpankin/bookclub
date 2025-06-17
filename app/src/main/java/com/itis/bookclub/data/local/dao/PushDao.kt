package com.itis.bookclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itis.bookclub.data.local.entity.PushEntity

@Dao
interface PushDao {
    @Insert
    suspend fun insert(push: PushEntity)

    @Query("SELECT * FROM pushes WHERE category = :category")
    suspend fun getByCategory(category: String): List<PushEntity>
}