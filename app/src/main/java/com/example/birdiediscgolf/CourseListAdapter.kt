package com.example.birdiediscgolf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseListAdapter internal constructor(context: Context) : RecyclerView.Adapter<CourseListAdapter.CourseNameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var courseAndHoles = emptyList<CourseAndHoles>()
    private var players = emptyList<Player>()
    private var gamesData = emptyList<GameData>()
    private var records = mutableListOf<Int?>()

    var onItemClick: ((CourseAndHoles) -> Unit)? = null

    inner class CourseNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(courseAndHoles[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseNameViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_course, parent, false)
        return CourseNameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseNameViewHolder, position: Int) {
        val course = courseAndHoles[position].course

        val courseName = course.name

        val courseRecord = getCourseRecord(course, position)

        val text = "$courseName Record: $courseRecord"

        holder.wordItemView.text = text

    }

   internal fun setGamesData(gamesData: List<GameData>) {
       this.gamesData = gamesData
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

    override fun getItemCount() = courseAndHoles.size

    private fun getGameScore(holes: List<GameHole>, gameScores: List<Score>) : Int {
        var result = 0

        for (i in gameScores.indices)
        {
            val hole = holes.find { hole -> hole.uuid == gameScores[i].gameHoleUuid }
            if (gameScores[i].score == -1) {
                return 999
            }
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

        val courseGames = gamesData.filter { game -> game.game.courseUuid == course.uuid }
        val courseHoleCount = courseAndHoles[idx].holes.filter { hole -> hole.courseUuid == course.uuid }.size

        for (game in courseGames){
            if (game.scores.size != courseHoleCount) {
                continue
            }

            game.players.find { gamePlayer -> gamePlayer.playerUuid == ownerPlayer!!.uuid } ?: continue

            val score = getGameScore(game.holes, game.scores)

            if (records[idx] == null){
                records[idx] = score
            } else if (records[idx]!! > score) {
                records[idx] = score
            }

        }
        return records[idx]
    }

}
