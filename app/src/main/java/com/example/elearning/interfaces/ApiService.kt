package com.example.elearning.interfaces

import com.example.elearning.models.CoursesModel
import retrofit2.http.GET

interface ApiService {
    @GET("bd31cccb-0d12-42ca-ba07-6b53c2d735b1")
    suspend fun getData(): CoursesModel
}
