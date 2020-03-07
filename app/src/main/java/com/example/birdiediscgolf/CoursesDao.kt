package com.example.birdiediscgolf

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHole(hole: Hole)

    @Query("SELECT * FROM courses ORDER BY name ASC")
    fun getAlphabetizedCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM holes WHERE courseUuid = :courseUuid ORDER BY hole ASC")
    fun getCourseHoles(courseUuid: String): List<Hole>

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()

    @Query("DELETE FROM holes")
    suspend fun deleteAllHoles()
}