package com.example.birdiediscgolf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class CourseSelectActivity : AppCompatActivity(), AddCourseDialog.DialogListener{

    private lateinit var birdieViewModel: BirdieViewModel
    private lateinit var playersList: List<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_select)

        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CourseListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.onItemClick = { courseAndHoles ->
            val intent = Intent(this, GameActivity::class.java)
            val gson = Gson()
            intent.putExtra("course", gson.toJson(courseAndHoles))
            intent.putExtra("players", gson.toJson(playersList))
            startActivity(intent)
        }

        birdieViewModel.allGamesData.observe(this, Observer { gamesData ->
            gamesData?.let { adapter.setGamesData(it)}
        })

        birdieViewModel.allCourseAndHoles.observe(this, Observer { courseAndHoles ->
            courseAndHoles?.let { adapter.setCourseAndHoles(it) }
        })

        birdieViewModel.allPlayers.observe(this, Observer { players ->
            players?.let { adapter.setPlayers(it)
            playersList = players}
        })

        val fab: FloatingActionButton = findViewById(R.id.addCourseFab)
        fab.setOnClickListener { view ->
            openAddCourseDialog()
        }
    }

    private fun openAddCourseDialog() {
        val addCourseDialog = AddCourseDialog()
        addCourseDialog.show(supportFragmentManager, "diggoo")
    }

    override fun getDialogInput(courseName: String, holeCount: Int) {
        super.getDialogInput(courseName, holeCount)
        Toast.makeText(applicationContext, courseName + holeCount.toString(), Toast.LENGTH_LONG).show()
    }
}
