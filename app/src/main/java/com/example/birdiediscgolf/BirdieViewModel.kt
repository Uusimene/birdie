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

    init {
        val coursesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).coursesDao()
        val playersDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).playersDao()
        val gamesDao = BirdieRoomDatabase.getDatabase(application, viewModelScope).gamesDao()
        repository = BirdieRepository(coursesDao, playersDao, gamesDao)
        allCourses = repository.allCourses
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
            if (updatedAt != null)
            {
                updatedAtLong = updatedAt.toLong()
            }
            val hole = Hole(uuid, courseUuid, createdAtLong, holeNumber, idInt, par, updatedAtLong)
            insertHole(hole)
        }
    }

    fun getCourseHoles(courseUuid: String): List<Hole> {
        return repository.getCourseHoles(courseUuid)
    }
}