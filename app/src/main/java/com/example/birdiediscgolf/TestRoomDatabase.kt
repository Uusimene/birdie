package com.example.birdiediscgolf

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.jvm.Volatile

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class TestRoomDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    private class testDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.testDao())
                }
            }
        }

        suspend fun populateDatabase(testDao: TestDao) {
            // Delete all content here.
            testDao.deleteAll()

            // Add sample words.
            var word = Word("Diggoo")
            testDao.insert(word)
            word = Word("Mies")
            testDao.insert(word)
        }

    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TestRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): TestRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TestRoomDatabase::class.java,
                        "test_database"
                ).addCallback(testDatabaseCallback(scope))
                 .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}