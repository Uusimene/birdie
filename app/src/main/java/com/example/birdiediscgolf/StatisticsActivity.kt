package com.example.birdiediscgolf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StatisticsActivity : AppCompatActivity() {

    private lateinit var birdieViewModel: BirdieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CourseListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        birdieViewModel.allGames.observe(this, Observer { games ->
            games?.let { adapter.setGames(it) }
        })

        birdieViewModel.allCourseAndHoles.observe(this, Observer { courseAndHoles ->
            courseAndHoles?.let {
                adapter.setCourseAndHoles(courseAndHoles) }
        })

        birdieViewModel.allGameHoles.observe(this, Observer { gameHoles ->
            gameHoles?.let { adapter.setGameHoles(it) }
        })

        birdieViewModel.allScores.observe(this, Observer { scores ->
            scores?.let { adapter.setScores(it) }
        })

        birdieViewModel.allGamePlayers.observe(this, Observer { gamePlayers ->
            gamePlayers?.let { adapter.setGamePlayers(it) }
        })

        birdieViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { adapter.setPlayers(it) }
        })

    }
}
