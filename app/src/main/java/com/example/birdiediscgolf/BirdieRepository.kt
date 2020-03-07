package com.example.birdiediscgolf

import androidx.lifecycle.LiveData

class BirdieRepository(private val coursesDao: CoursesDao) {

    val allCourses: LiveData<List<Course>> = coursesDao.getAlphabetizedCourses()

    suspend fun insertCourse(course: Course) {
        coursesDao.insertCourse(course)
    }

    suspend fun insertHole(hole: Hole) {
        coursesDao.insertHole(hole)
    }

    fun getCourseHoles(courseUuid: String): List<Hole> {
        return coursesDao.getCourseHoles(courseUuid)
    }
}