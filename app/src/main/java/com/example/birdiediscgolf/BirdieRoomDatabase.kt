package com.example.birdiediscgolf

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Course::class, Hole::class, Player::class, Game::class,
                GamePlayer::class, GameHole::class, Score::class],
    version = 1,
    exportSchema = false
)
public abstract class BirdieRoomDatabase : RoomDatabase() {

    abstract fun coursesDao(): CoursesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BirdieRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): BirdieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BirdieRoomDatabase::class.java,
                    "test_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}