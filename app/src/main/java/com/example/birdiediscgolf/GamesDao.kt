package com.example.birdiediscgolf

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Query("SELECT * FROM games ORDER BY createdAt DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM gameHoles ORDER BY createdAt DESC")
    fun getAllGameHoles(): LiveData<List<GameHole>>

    @Query("SELECT * FROM scores ORDER BY createdAt DESC")
    fun getAllScores(): LiveData<List<Score>>

    @Query("SELECT * FROM gamePlayers ORDER BY createdAt DESC")
    fun getAllGamePlayers(): LiveData<List<GamePlayer>>

    @Query("SELECT COUNT(*) FROM games")
    fun getGameCount(): LiveData<Int>

    @Query("SELECT * FROM games ORDER BY createdAt DESC")
    fun getAllGamesData(): LiveData<List<GameData>>
}