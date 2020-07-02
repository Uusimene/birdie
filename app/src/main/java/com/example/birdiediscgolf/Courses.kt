package com.example.birdiediscgolf

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "courses")
data class Course (
    @PrimaryKey
    val uuid: String = "",

    val id: Int = -1,

    val name: String = "",

    val createdAt: Long = -1,

    val record: Int = -1
    )

@Entity(tableName = "holes")
data class Hole (
    @PrimaryKey
    val uuid: String = "",

    val courseUuid: String = "",

    val createdAt: Long = -1,

    val hole: Int = -1,

    val id: Int = -1,

    val par: Int = -1,

    val updatedAt: Long?,

    val bestScore: Int = -1
)

data class CourseAndHoles (
    @Embedded
    val course: Course = Course(),
    @Relation(parentColumn = "uuid", entityColumn = "courseUuid")
    val holes: List<Hole> = emptyList()
)