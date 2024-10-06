package com.example.elearning.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearning.models.Result
import com.example.elearning.room.courses.CoursesRepository
import kotlinx.coroutines.launch

class CoursesViewModel(private val repository: CoursesRepository) : ViewModel() {
    val allCourses: LiveData<List<Result>> = repository.getAllCourses()

    suspend fun getCourseById(id: Int): Result? {
        return repository.getCourseById(id)
    }

    fun insert(course: Result) {
        viewModelScope.launch {
            repository.insert(course)
        }
    }

    suspend fun deleteCourse(id: Int) {
        repository.deleteCourse(id)
    }

    suspend fun deleteAllCourses() {
        repository.deleteAllCourses()
    }
}