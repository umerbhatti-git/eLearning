package com.example.elearning.room.courses

import androidx.lifecycle.LiveData
import com.example.elearning.models.Result

class CoursesRepository(private val courseDao: CoursesDao) {
    fun getAllCourses(): LiveData<List<Result>> {
        return courseDao.getAllCourses()
    }

    suspend fun getCourseById(courseId: Int): Result? {
        return courseDao.getCourseById(courseId)
    }

    suspend fun insert(course: Result) {
        courseDao.insert(course)
    }

    suspend fun deleteCourse(courseId: Int) {
        courseDao.deleteCourse(courseId)
    }

    suspend fun deleteAllCourses() {
        courseDao.deleteAllCourses()
    }
}