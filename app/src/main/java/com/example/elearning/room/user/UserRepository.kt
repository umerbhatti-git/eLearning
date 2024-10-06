package com.example.elearning.room.user

import com.example.elearning.models.UserModel

class UserRepository(private val userDao: UserDao) {
    suspend fun getUser(): UserModel? {
        return userDao.getUser()
    }

    suspend fun insert(user: UserModel) {
        userDao.insert(user)
    }

    suspend fun deleteUser() {
        userDao.deleteUser()
    }
}