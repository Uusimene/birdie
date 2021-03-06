package com.example.birdiediscgolf

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: Player)

    @Query("SELECT * FROM players ORDER BY name ASC")
    fun getPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players WHERE owner = 1")
    suspend fun getOwnerPlayer(): Player

    @Transaction
    suspend fun insertPlayers(players: List<Player>) {
        for (player in players) {
            insertPlayer(player)
        }
    }
}