package com.example.birdiediscgolf

import androidx.lifecycle.LiveData

class BirdieRepository(private val coursesDao: CoursesDao) {

    val allCourses: LiveData<List<Course>> = coursesDao.getAlphabetizedCourses()

    suspend fun insertCourse(course: Course) {
        coursesDao.insertCourse(course)
    }
}