package com.example.birdiediscgolf

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Transaction
    suspend fun insertGames(games: List<Game>) {
        for (game in games) {
            insertGame(game)
        }
    }

    @Transaction
    suspend fun insertGamePlayers(gamePlayers: List<GamePlayer>) {
        for (player in gamePlayers) {
            insertGamePlayer(player)
        }
    }

    @Transaction
    suspend fun insertGameHoles(gameHoles: List<GameHole>) {
        for (gameHole in gameHoles) {
            insertGameHole(gameHole)
        }
    }

    @Transaction
    suspend fun insertScores(scores: List<Score>) {
        for (score in scores) {
            insertScore(score)
        }
    }

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