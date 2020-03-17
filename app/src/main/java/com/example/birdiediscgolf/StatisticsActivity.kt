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

        birdieViewModel.allGamesData.observe(this, Observer { gamesData ->
            gamesData?.let { adapter.setGamesData(it)}
        })

        birdieViewModel.allCourseAndHoles.observe(this, Observer { courseAndHoles ->
            courseAndHoles?.let { adapter.setCourseAndHoles(it) }
        })

        birdieViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { adapter.setPlayers(it) }
        })

    }
}
