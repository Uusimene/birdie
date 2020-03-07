package com.example.birdiediscgolf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course (
    @PrimaryKey
    val uuid: String,

    val id: Int,

    val name: String,

    val createdAt: Long
    )

@Entity(tableName = "holes")
data class Hole (
    @PrimaryKey
    val uuid: String,

    val courseUuid: String,

    val createdAt: Long,

    val hole: Int,

    val id: Int,

    val par: Int,

    val updatedAt: Long?
)