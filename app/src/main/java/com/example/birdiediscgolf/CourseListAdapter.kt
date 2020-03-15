package com.example.birdiediscgolf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseListAdapter internal constructor(context: Context) : RecyclerView.Adapter<CourseListAdapter.CourseNameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var games = emptyList<Game>() // Cached copies
    private var gameHoles = emptyList<GameHole>()
    private var gamePlayers = emptyList<GamePlayer>()
    private var scores = emptyList<Score>()
    private var courseAndHoles = emptyList<CourseAndHoles>()
    private var players = emptyList<Player>()
    private var records = mutableListOf<Int?>()
    private lateinit var ownerUser: Player

    inner class CourseNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseNameViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_course, parent, false)
        return CourseNameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseNameViewHolder, position: Int) {
        val course = courseAndHoles[position].course ?: return

        val courseName = course.name

        val courseRecord = getCourseRecord(course, position)

        val text = "$courseName Record: $courseRecord"

        holder.wordItemView.text = text

    }

    internal fun setGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    internal fun setGamePlayers(gamePlayers: List<GamePlayer>) {
        this.gamePlayers = gamePlayers
        notifyDataSetChanged()
    }

    internal fun setScores(scores: List<Score>) {
        this.scores = scores
        notifyDataSetChanged()
    }

    internal fun setCourseAndHoles(courses: List<CourseAndHoles>) {
        this.courseAndHoles = courses
        notifyDataSetChanged()
    }

    internal fun setPlayers(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

    internal fun setGameHoles(gameHoles: List<GameHole>) {
        this.gameHoles = gameHoles
        notifyDataSetChanged()
    }

    override fun getItemCount() = courseAndHoles.size

    private fun getGameScore(holes: List<GameHole>, gameScores: List<Score>) : Int {
        var result = 0

        for (i in gameScores.indices)
        {
            val hole = holes.find { hole -> hole.uuid == gameScores[i].gameHoleUuid }
            result += gameScores[i].score - hole!!.par
        }

        return result
    }

    private fun getCourseRecord(course: Course, idx: Int) : Int? {
        val ownerPlayer = players.find { player -> player.owner == 1 }
        if (records.isEmpty()) {
            val recordsResult = mutableListOf<Int?>()
            for (i in courseAndHoles.indices){
                recordsResult.add(null)
            }
            records = recordsResult
        }

        val courseGames = games.filter { game -> game.courseUuid == course.uuid }
        val courseHoleCount = courseAndHoles[idx].holes?.filter { hole -> hole.courseUuid == course.uuid }?.size

        for (game in courseGames){
            val holes = gameHoles.filter { gameHole -> gameHole.gameUuid == game.uuid }
            if (holes.size != courseHoleCount) {
                continue
            }
            val filteredGamePlayers = gamePlayers.filter { gamePlayer -> gamePlayer.gameUuid == game.uuid }
            val ownerGamePlayer = filteredGamePlayers.find { gamePlayer -> gamePlayer.playerUuid == ownerPlayer!!.uuid } ?: continue


            val gameScores = scores.filter { score -> score.gameUuid == game.uuid && score.gamePlayerUuid == ownerGamePlayer.uuid}

            val score = getGameScore(holes, gameScores)

            if (records[idx] == null){
                records[idx] = score
            } else if (records[idx]!! > score) {
                records[idx] = score
            }

        }
        return records[idx]
    }

    fun setOwnerPlayer(player: Player) {
        this.ownerUser = player
    }

}
