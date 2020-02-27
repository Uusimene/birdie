package com.example.birdiediscgolf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game (
    @PrimaryKey
    val uuid: String,

    val courseUuid: String,

    val createdAt: Int,

    val endedAt: Int,

    val id: Int,

    val StartedAt: Int,

    val updatedAt: Int
)

@Entity(tableName = "gamePlayers")
data class GamePlayer (
    @PrimaryKey
    val uuid: String,

    val gameUuid: String,

    val createdAt: Int,

    val id: Int,

    val playerUuid: String
)

@Entity(tableName = "gameHoles")
data class GameHole (
    @PrimaryKey
    val uuid: String,

    val gameUuid: String,

    val createdAt: Int,

    val hole: Int,

    val id: Int,

    val par: Int
)

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey
    val uuid: String,

    val createdAt: Int,

    val gameHoleUuid: String,

    val gamePlayerUuid: String,

    val gameUuid: String,

    val id: Int,

    val score: Int
)