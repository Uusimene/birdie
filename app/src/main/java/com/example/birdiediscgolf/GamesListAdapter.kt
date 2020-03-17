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
    private var courses = emptyList<Course>()
    private var players = emptyList<Player>()
    private var gamesData = emptyList<GameData>()

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
        val game = gamesData[position]
        val course = courses.find { course -> course.uuid == game.game.courseUuid }
        val holes = game.holes
        val gamePlayers = game.players
        val ownerPlayer = players.find { player -> player.owner == 1 } ?: return
        val ownerGamePlayer = gamePlayers.find { gamePlayer -> gamePlayer.playerUuid == ownerPlayer.uuid } ?: return
        val gameScores = game.scores.filter { score -> score.gamePlayerUuid == ownerGamePlayer.uuid}

        if (course != null){
            val courseName = course.name
            val endedAt = Timestamp(game.game.endedAt).toString()
            val gameScore = getGameScore(holes, gameScores)
            var scoreString = gameScore.toString()
            if (gameScore > 0)
            {
                scoreString = "+$gameScore"
            }
            holder.wordItemView.text = courseName
            holder.dateItemView.text = endedAt
            holder.scoreItemView.text = scoreString
        }
    }

    internal fun setCourses(courses: List<Course>) {
        this.courses = courses
        notifyDataSetChanged()
    }

    internal fun setPlayers(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

    internal fun setGamesData(gamesData: List<GameData>) {
        this.gamesData = gamesData
        notifyDataSetChanged()
    }

    override fun getItemCount() = gamesData.size

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
