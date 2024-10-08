package com.example.elearning.interfaces

import com.example.elearning.models.CoursesModel
import retrofit2.http.GET

interface ApiService {
    @GET("5fc9dda8-cbed-4e57-9c39-855e89dcfbaa")
    suspend fun getData(): CoursesModel
}
