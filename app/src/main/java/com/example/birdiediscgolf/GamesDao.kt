package com.example.birdiediscgolf

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGamePlayer(gamePlayer: GamePlayer)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameHole(gameHole: GameHole)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScore(score: Score)
}