package com.example.birdiediscgolf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player (
    @PrimaryKey
    val uuid: String,

    val createdAt: Long,

    val id: Int,

    val name: String,

    val owner: Int,

    val profileImageFilename: String?,

    val updatedAt: Long?
)