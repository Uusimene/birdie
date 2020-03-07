package com.example.birdiediscgolf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game (
    @PrimaryKey
    val uuid: String,

    val courseUuid: String,

    val createdAt: Long,

    val endedAt: Long,

    val id: Int,

    val StartedAt: Long,

    val updatedAt: Long
)

@Entity(tableName = "gamePlayers")
data class GamePlayer (
    @PrimaryKey
    val uuid: String,

    val gameUuid: String,

    val createdAt: Long,

    val id: Int,

    val playerUuid: String
)

@Entity(tableName = "gameHoles")
data class GameHole (
    @PrimaryKey
    val uuid: String,

    val gameUuid: String,

    val createdAt: Long,

    val hole: Int,

    val id: Int,

    val par: Int
)

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey
    val uuid: String,

    val createdAt: Long,

    val gameHoleUuid: String,

    val gamePlayerUuid: String,

    val gameUuid: String,

    val id: Int,

    val score: Int
)