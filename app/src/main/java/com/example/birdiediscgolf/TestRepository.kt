package com.example.birdiediscgolf

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TestRepository(private val testDao: TestDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = testDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        testDao.insert(word)
    }
}