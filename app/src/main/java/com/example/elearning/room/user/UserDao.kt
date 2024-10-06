package com.example.elearning.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elearning.models.UserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserModel?

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}