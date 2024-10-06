package com.example.elearning.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String
) {
    @PrimaryKey
    var id: Int = 0
}