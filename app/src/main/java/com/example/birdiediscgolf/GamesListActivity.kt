package com.example.birdiediscgolf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GamesListActivity : AppCompatActivity() {

    private lateinit var birdieViewModel: BirdieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)

        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = GamesListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        birdieViewModel.allCourseAndHoles.observe(this, Observer { courseAndHoles ->
            courseAndHoles?.let {
                val courses = mutableListOf<Course>()
                for (item in it) {
                    courses.add(item.course)
                }
                adapter.setCourses(courses) }
        })

        birdieViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { adapter.setPlayers(it) }
        })

        birdieViewModel.allGamesData.observe(this, Observer { gamesData ->
            gamesData?.let { adapter.setGamesData(it) }
        })

    }
}
