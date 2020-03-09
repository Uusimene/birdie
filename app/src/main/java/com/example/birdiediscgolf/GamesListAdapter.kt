package com.example.birdiediscgolf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Timestamp

class GamesListAdapter internal constructor (context: Context) : RecyclerView.Adapter<GamesListAdapter.GameInfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var games = emptyList<Game>() // Cached copies
    private var gameHoles = emptyList<GameHole>()
    private var gamePlayers = emptyList<GamePlayer>()
    private var scores = emptyList<Score>()
    private var courses = emptyList<Course>()
    private var holes = emptyList<Hole>()
    private var players = emptyList<Player>()

    inner class GameInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
        val dateItemView: TextView = itemView.findViewById(R.id.dateTextView)
        val scoreItemView: TextView = itemView.findViewById(R.id.scoreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_game, parent, false)
        return GameInfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        val game = games[position]
        val course = courses.find { course -> course.uuid == game.courseUuid }
        val gameScores = scores.filter { score -> score.gameUuid == game.uuid }
        val holes = gameHoles.filter { gameHole -> gameHole.gameUuid == game.uuid }
        if (course != null){
            val courseName = course.name
            val endedAt = Timestamp(game.endedAt).toString()
            val gameScore = getGameScore(holes, gameScores)
            holder.wordItemView.text = courseName
            holder.dateItemView.text = endedAt
            holder.scoreItemView.text = gameScore.toString()
        }
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

    internal fun setCourses(courses: List<Course>) {
        this.courses = courses
        notifyDataSetChanged()
    }

    internal fun setHoles(holes: List<Hole>) {
        this.holes = holes
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

    override fun getItemCount() = games.size

    private fun getGameScore(holes: List<GameHole>, gameScores: List<Score>) : Int {
        var result = 0

        for (i in gameScores.indices)
        {
            val hole = holes.find { hole -> hole.uuid == gameScores[i].gameHoleUuid }
            result += gameScores[i].score - hole!!.par
        }

        return result
    }
}
