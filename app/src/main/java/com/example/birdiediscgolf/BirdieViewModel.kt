package com.example.birdiediscgolf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class BirdieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BirdieRepository

    val allCourses: LiveData<List<Course>>
    val allGames: LiveData<List<Game>>
    val allGameHoles: LiveData<List<GameHole>>
    val allScores: LiveData<List<Score>>
    val gameCount: LiveData<Int>

    init {
        val coursesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).coursesDao()
        val playersDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).playersDao()
        val gamesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).gamesDao()
        repository = BirdieRepository(coursesDao, playersDao, gamesDao)
        allCourses = repository.allCourses
        allGames = repository.allGames
        gameCount = repository.gameCount
        allGameHoles = repository.allGameHoles
        allScores = repository.allScores
    }

    fun insertCourse(course: Course) = viewModelScope.launch {
        repository.insertCourse(course)
    }

    fun insertHole(hole: Hole) = viewModelScope.launch {
        repository.insertHole(hole)
    }

    fun insertPlayer(player: Player) = viewModelScope.launch {
        repository.insertPlayer(player)
    }

    fun insertGame(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun insertGamePlayer(gamePlayer: GamePlayer) = viewModelScope.launch {
        repository.insertGamePlayer(gamePlayer)
    }

    fun insertGameHole(gameHole: GameHole) = viewModelScope.launch {
        repository.insertGameHole(gameHole)
    }

    fun insertScore(score: Score) = viewModelScope.launch {
        repository.insertScore(score)
    }

    fun importCourses(courses: JSONArray) {
        for (i in 0 until courses.length()) {
            val courseObject = courses[i] as JSONObject

            val createdAt = courseObject.get("createdAt") as String
            val id = courseObject.get("id") as String
            val name = courseObject.get("name") as String
            val uuid = courseObject.get("uuid") as String

            val createdAtLong = createdAt.toLong()
            val idInt = id.toInt()

            val course = Course(uuid, idInt, name, createdAtLong)
            insertCourse(course)
        }
    }

    fun importHoles(holes: JSONArray) {
        for (i in 0 until holes.length()) {
            val holeObject = holes[i] as JSONObject

            val createdAt = holeObject.get("createdAt") as String
            val courseUuid = holeObject.get("courseUuid") as String
            val holeNumber = holeObject.get("hole") as Int
            val id = holeObject.get("id") as String
            val par = holeObject.get("par") as Int
            val uuid = holeObject.get("uuid") as String
            var updatedAt: String? = null
            if (holeObject.has("updatedAt")){
                updatedAt = holeObject.get("updatedAt") as String
            }

            val idInt = id.toInt()
            val createdAtLong = createdAt.toLong()
            var updatedAtLong: Long? = null
            if (updatedAt != null) {
                updatedAtLong = updatedAt.toLong()
            }
            val hole = Hole(uuid, courseUuid, createdAtLong, holeNumber, idInt, par, updatedAtLong)
            insertHole(hole)
        }
    }

    fun importPlayers(players: JSONArray) {
        for (i in 0 until players.length()) {
            val playerObject = players[i] as JSONObject

            val createdAt = playerObject.get("createdAt") as String
            val id = playerObject.get("id") as String
            val name = playerObject.get("name") as String
            val owner = playerObject.get("owner") as Int
            val uuid = playerObject.get("uuid") as String
            var updatedAt: String? = null
            if (playerObject.has("updatedAt")) {
                updatedAt = playerObject.get("updatedAt") as String
            }
            var profileImageFilename: String? = null
            if (playerObject.has("profileImageFilename")) {
                profileImageFilename = playerObject.get("profileImageFilename") as String
            }

            val createdAtLong = createdAt.toLong()
            val idInt = id.toInt()
            var updatedAtLong: Long? = null
            if (updatedAt != null) {
                updatedAtLong = updatedAt.toLong()
            }
            val player = Player(uuid, createdAtLong, idInt, name, owner, profileImageFilename, updatedAtLong )
            insertPlayer(player)
        }
    }

    fun importGames(games: JSONArray) {
        for (i in 0 until games.length()) {
            val gameObject = games[i] as JSONObject

            val courseUuid = gameObject.get("courseUuid") as String
            val createdAt = gameObject.get("createdAt") as String
            val endedAt = gameObject.get("endedAt") as String
            val id = gameObject.get("id") as String
            val startedAt = gameObject.get("startedAt") as String
            val updatedAt = gameObject.get("updatedAt") as String
            val uuid = gameObject.get("uuid") as String

            val createdAtLong = createdAt.toLong()
            val endedAtLong = endedAt.toLong()
            val startedAtLong = startedAt.toLong()
            val updatedAtLong = updatedAt.toLong()
            val idInt = id.toInt()

            val game = Game(uuid, courseUuid, createdAtLong, endedAtLong, idInt, startedAtLong, updatedAtLong)
            insertGame(game)
        }
    }

    fun importGamePlayers(gamePlayers: JSONArray) {
        for (i in 0 until gamePlayers.length()) {
            val gamePlayerObject = gamePlayers[i] as JSONObject

            val createdAt = gamePlayerObject.get("createdAt") as String
            val gameUuid = gamePlayerObject.get("gameUuid") as String
            val id = gamePlayerObject.get("id") as String
            val playerUuid = gamePlayerObject.get("playerUuid") as String
            val uuid = gamePlayerObject.get("uuid") as String

            val idInt = id.toInt()
            val createdAtLong = createdAt.toLong()

            val gamePlayer = GamePlayer(uuid, gameUuid, createdAtLong, idInt, playerUuid)
            insertGamePlayer(gamePlayer)
        }
    }

    fun importGameHoles(gameHoles: JSONArray) {
        for (i in 0 until gameHoles.length()) {
            val gameHoleObject = gameHoles[i] as JSONObject

            val createdAt = gameHoleObject.get("createdAt") as String
            val gameUuid = gameHoleObject.get("gameUuid") as String
            val hole = gameHoleObject.get("hole") as Int
            val id = gameHoleObject.get("id") as String
            val par = gameHoleObject.get("par") as Int
            val uuid = gameHoleObject.get("uuid") as String

            val idInt = id.toInt()
            val createdAtLong = createdAt.toLong()

            val gameHole = GameHole(uuid, gameUuid, createdAtLong, hole, idInt, par)
            insertGameHole(gameHole)
        }
    }

    fun importScores(scores: JSONArray) {
        for (i in 0 until scores.length()) {
            val scoreObject = scores[i] as JSONObject

            val createdAt = scoreObject.get("createdAt") as String
            val gameHoleUuid = scoreObject.get("gameHoleUuid") as String
            val gamePlayerUuid = scoreObject.get("gamePlayerUuid") as String
            val gameUuid = scoreObject.get("gameUuid") as String
            val id = scoreObject.get("id") as String
            val scoreVal = scoreObject.get("score") as Int
            val uuid = scoreObject.get("uuid") as String

            val createdAtLong = createdAt.toLong()
            val idInt = id.toInt()

            val score = Score(uuid, createdAtLong, gameHoleUuid, gamePlayerUuid, gameUuid, idInt, scoreVal)
            insertScore(score)
        }
    }

    fun getCourseHoles(courseUuid: String): List<Hole> {
        return repository.getCourseHoles(courseUuid)
    }

}