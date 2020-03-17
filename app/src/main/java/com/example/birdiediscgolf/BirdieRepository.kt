package com.example.birdiediscgolf

import androidx.lifecycle.LiveData

class BirdieRepository(private val coursesDao: CoursesDao, private val playersDao: PlayersDao, private val gamesDao: GamesDao) {

    //val allCourses: LiveData<List<Course>> = coursesDao.getAlphabetizedCourses()
    //val allHoles: LiveData<List<Hole>> = coursesDao.getAllHoles()
    val allCourseAndHoles: LiveData<List<CourseAndHoles>> = coursesDao.getAllCourseAndHoles()
    val allGames: LiveData<List<Game>> = gamesDao.getAllGames()
    val allGameHoles: LiveData<List<GameHole>> = gamesDao.getAllGameHoles()
    val allScores: LiveData<List<Score>> = gamesDao.getAllScores()
    val allGamePlayers: LiveData<List<GamePlayer>> = gamesDao.getAllGamePlayers()
    val allPlayers: LiveData<List<Player>> = playersDao.getPlayers()
    val allGameData: LiveData<List<GameData>> = gamesDao.getAllGamesData()

    val gameCount: LiveData<Int> = gamesDao.getGameCount()

    suspend fun insertCourse(course: Course) {
        coursesDao.insertCourse(course)
    }

    suspend fun insertHole(hole: Hole) {
        coursesDao.insertHole(hole)
    }

    suspend fun getCourseHoles(courseUuid: String): List<Hole> {
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

    suspend fun getOwnerPlayer(): Player {
        return playersDao.getOwnerPlayer()
    }

}
