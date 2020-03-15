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
    suspend fun getCourseHoles(courseUuid: String): List<Hole>

    @Query("SELECT * FROM holes ORDER BY courseUuid ASC")
    fun getAllHoles(): LiveData<List<Hole>>

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()

    @Query("DELETE FROM holes")
    suspend fun deleteAllHoles()

    //@Query("SELECT * FROM courses INNER JOIN holes ON courses.uuid = holes.courseUuid")
    //@Query("SELECT courses.uuid AS cUuid, courses.id AS cId, courses.name AS cName, courses.createdAt AS cCreatedAt, holes.uuid AS hUuid, holes.courseUuid AS hCourseUuid, holes.createdAt AS hCreatedAt, holes.hole AS hHole, holes.id AS hId, holes.par AS hPar, holes.updatedAt AS hUpdatedAt FROM courses INNER JOIN holes ON courses.uuid = holes.courseUuid")
    @Query("SELECT * FROM courses")
    fun getAllCourseAndHoles(): LiveData<List<CourseAndHoles>>

}
