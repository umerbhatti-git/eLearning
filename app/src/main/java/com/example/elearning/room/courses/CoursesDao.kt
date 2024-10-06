package com.example.elearning.room.courses

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elearning.models.Result

@Dao
interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Result)

    @Query("SELECT * FROM courses WHERE id = :courseId LIMIT 1")
    suspend fun getCourseById(courseId: Int): Result?

    @Query("SELECT * FROM courses")
    fun getAllCourses(): LiveData<List<Result>>

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteCourse(courseId: Int)

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()
}