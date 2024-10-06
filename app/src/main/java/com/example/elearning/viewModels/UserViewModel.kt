package com.example.elearning.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearning.models.UserModel
import com.example.elearning.room.user.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun getUser(): UserModel? {
        return repository.getUser()
    }

    fun insert(user: UserModel) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    suspend fun deleteUser() {
        repository.deleteUser()
    }
}