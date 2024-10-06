package com.example.elearning.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "courses")
data class Result(
    val details: String,
    @PrimaryKey val id: Int,
    val price: String,
    val title: String
) : Parcelable