package com.example.birdiediscgolf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class BirdieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BirdieRepository

    val allCourseAndHoles: LiveData<List<CourseAndHoles>>
    val allPlayers: LiveData<List<Player>>
    val gameCount: LiveData<Int>
    val allGamesData: LiveData<List<GameData>>
    private var ownerUserPlayer: Player? = null

    init {
        val coursesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).coursesDao()
        val playersDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).playersDao()
        val gamesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).gamesDao()
        repository = BirdieRepository(coursesDao, playersDao, gamesDao)

        gameCount = repository.gameCount
        allPlayers = repository.allPlayers
        allCourseAndHoles = repository.allCourseAndHoles
        allGamesData = repository.allGameData
    }

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            BirdieRoomDatabase.getDatabase(getApplication(), viewModelScope).clearAllTables()
        }
    }

    fun updateHolePar(holeUuid: String, par: Int) = viewModelScope.launch {
        repository.updateHolePar(holeUuid, par)
    }

    fun insertCourse(course: Course) = viewModelScope.launch {
        repository.insertCourse(course)
    }

    private fun insertCourses(courses: List<Course>) = viewModelScope.launch {
        repository.insertCourses(courses)
    }

    private fun insertHole(hole: Hole) = viewModelScope.launch {
        repository.insertHole(hole)
    }

    fun insertHoles(holes: List<Hole>) = viewModelScope.launch {
        repository.insertHoles(holes)
    }

    private fun updateHoleRecord(holeUuid: String, score: Int) = viewModelScope.launch {
        val courseandholes = allCourseAndHoles.value
        if (courseandholes != null){
            val holes = mutableListOf<List<Hole>>()
            for (course in courseandholes) {
                holes.add(course.holes)
            }
            for (holelist in holes){
                for (hole in holelist){
                    val uuid = hole.uuid
                    repository.updateHoleRecord(uuid, 5)
                }
            }
        }

        // tää funktio toimii, ylläoleva paska ei
        //repository.updateHoleRecord(holeUuid, score)
    }

    fun insertPlayer(player: Player) = viewModelScope.launch {
        repository.insertPlayer(player)
    }

    private fun insertPlayers(players: List<Player>) = viewModelScope.launch {
        repository.insertPlayers(players)
    }

    fun insertGame(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun insertGames(games: List<Game>) = viewModelScope.launch {
        repository.insertGames(games)
    }

    fun insertGamePlayer(gamePlayer: GamePlayer) = viewModelScope.launch {
        repository.insertGamePlayer(gamePlayer)
    }

    fun insertGamePlayers(gamePlayers: List<GamePlayer>) = viewModelScope.launch {
        repository.insertGamePlayers(gamePlayers)
    }

    private fun insertGameHole(gameHole: GameHole) = viewModelScope.launch {
        repository.insertGameHole(gameHole)
    }

    fun insertGameHoles(gameHoles: List<GameHole>) = viewModelScope.launch {
        repository.insertGameHoles(gameHoles)
    }

    private fun insertScore(score: Score) = viewModelScope.launch {
        repository.insertScore(score)
    }

    fun insertScores(scores: List<Score>) = viewModelScope.launch {
        repository.insertScores(scores)
    }

    fun importCourses(courses: JSONArray) {
        val coursesList = mutableListOf<Course>()
        for (i in 0 until courses.length()) {
            val courseObject = courses[i] as JSONObject

            val createdAt = courseObject.get("createdAt") as String
            val id = courseObject.get("id") as String
            val name = courseObject.get("name") as String
            val uuid = courseObject.get("uuid") as String

            val createdAtLong = createdAt.toLong()
            val idInt = id.toInt()

            val course = Course(uuid, idInt, name, createdAtLong)
            coursesList.add(course)
            //insertCourse(course)
        }
        insertCourses(coursesList)
    }

    fun importHoles(holes: JSONArray) {
        val holesList = mutableListOf<Hole>()
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
            holesList.add(hole)
            //insertHole(hole)
        }
        insertHoles(holesList)
    }

    fun importPlayers(players: JSONArray) {
        val playersList = mutableListOf<Player>()
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
            playersList.add(player)
            // insertPlayer(player)
        }
        insertPlayers(playersList)
    }

    fun importGames(games: JSONArray) {
        val gamesList = mutableListOf<Game>()
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
            gamesList.add(game)
            //insertGame(game)
        }
        insertGames(gamesList)
    }

    fun importGamePlayers(gamePlayers: JSONArray) {
        val gamePlayersList = mutableListOf<GamePlayer>()
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
            gamePlayersList.add(gamePlayer)
            //insertGamePlayer(gamePlayer)
        }
        insertGamePlayers(gamePlayersList)
    }

    fun importGameHoles(gameHoles: JSONArray) {
        val gameHolesList = mutableListOf<GameHole>()
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
            gameHolesList.add(gameHole)
            //insertGameHole(gameHole)
        }
        insertGameHoles(gameHolesList)
    }

    fun importScores(scores: JSONArray) {
        val scoresList = mutableListOf<Score>()
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
            scoresList.add(score)
            //insertScore(score)
        }
        insertScores(scoresList)
    }

    suspend fun getCourseHoles(courseUuid: String): List<Hole> {
        return repository.getCourseHoles(courseUuid)
    }

    fun getOwnerPlayer(): Player {
        if (ownerUserPlayer == null) {
            viewModelScope.launch {
                ownerUserPlayer = repository.getOwnerPlayer()
            }
        }

        return ownerUserPlayer!!
    }

    fun updateHoleRecords() {
//        val ownerPlayer = allPlayers.value?.find { player -> player.owner == 1 }
//        val holeRecordMap = mutableMapOf<String, Int>()
//        val gamesData = allGamesData.value
//        val courseData = allCourseAndHoles.value
//        if (gamesData != null && courseData != null){
//            for (game in gamesData){
//                val courseAndHoles = courseData.filter {it.course.uuid == game.game.courseUuid}
//                val courseUuid = courseAndHoles[0].course.uuid
//                val course = courseAndHoles[0].course
//                val courseHoles = courseAndHoles[0].holes
//                for (score in game.scores){
//
//                }
//            }
//        }
        updateHoleRecord("asd", 1)
    }

}
