package com.example.birdiediscgolf

import androidx.lifecycle.LiveData

class BirdieRepository(private val coursesDao: CoursesDao, private val playersDao: PlayersDao, private val gamesDao: GamesDao) {

    val allCourses: LiveData<List<Course>> = coursesDao.getAlphabetizedCourses()

    suspend fun insertCourse(course: Course) {
        coursesDao.insertCourse(course)
    }

    suspend fun insertHole(hole: Hole) {
        coursesDao.insertHole(hole)
    }

    fun getCourseHoles(courseUuid: String): List<Hole> {
        return coursesDao.getCourseHoles(courseUuid)
    }

    suspend fun insertPlayer(player: Player) {
        playersDao.insertPlayer(player)
    }

    suspend fun insertGame(game: Game) {
        gamesDao.insertGame(game)
    }

    suspend fun insertGamePlayer(gamePlayer: GamePlayer) {
        gamesDao.insertGamePlayer(gamePlayer)
    }

    suspend fun insertGameHole(gameHole: GameHole) {
        gamesDao.insertGameHole(gameHole)
    }

    suspend fun insertScore(score: Score) {
        gamesDao.insertScore(score)
    }
}