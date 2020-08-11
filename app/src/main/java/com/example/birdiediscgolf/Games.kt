package com.example.birdiediscgolf

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "games")
data class Game (
    @PrimaryKey
    var uuid: String,

    var courseUuid: String,

    var createdAt: Long,

    var endedAt: Long,

    var id: Int,

    var StartedAt: Long,

    var updatedAt: Long
)

@Entity(tableName = "gamePlayers")
data class GamePlayer (
    @PrimaryKey
    var uuid: String,

    var gameUuid: String,

    var createdAt: Long,

    var id: Int,

    var playerUuid: String
)

@Entity(tableName = "gameHoles")
data class GameHole (
    @PrimaryKey
    var uuid: String,

    var gameUuid: String,

    var createdAt: Long,

    var hole: Int,

    var id: Int,

    var par: Int
)

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey
    var uuid: String,

    var createdAt: Long,

    var gameHoleUuid: String,

    var gamePlayerUuid: String,

    var gameUuid: String,

    var id: Int,

    var score: Int
)

data class GameData (
    @Embedded
    var game: Game,
    @Relation(parentColumn = "uuid", entityColumn = "gameUuid")
    var players: List<GamePlayer>,
    @Relation(parentColumn = "uuid", entityColumn = "gameUuid")
    var holes: List<GameHole>,
    @Relation(parentColumn = "uuid", entityColumn = "gameUuid")
    var scores: List<Score>
)

