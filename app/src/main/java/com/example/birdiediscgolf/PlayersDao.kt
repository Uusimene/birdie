package com.example.birdiediscgolf

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: Player)

    @Query("SELECT * FROM players ORDER BY name ASC")
    fun getPlayers(): List<Player>
}