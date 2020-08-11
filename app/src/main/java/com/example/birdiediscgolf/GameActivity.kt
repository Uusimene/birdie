package com.example.birdiediscgolf

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.birdiediscgolf.ui.main.GameSectionsPagerAdapter
import com.example.birdiediscgolf.ui.main.gameFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class GameActivity : AppCompatActivity(), gameFragment.OnScoreSelectedListener {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = courseAndHoles.course.name
        setSupportActionBar(toolbar)

        val sectionsPagerAdapter = GameSectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setCourseScores(courseAndHoles)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.endGame -> Toast.makeText(applicationContext, "end game pressed", Toast.LENGTH_SHORT).show()
            R.id.changePar -> Toast.makeText(applicationContext, "change par pressed", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        if (fragment is gameFragment){
            fragment.setOnScoreSelectedListener(this)
        }
        super.onAttachFragment(fragment)
    }

    override fun onScoreSelected(score: Int, hole: Int) {
        Toast.makeText(this, "score: $score, hole: $hole", Toast.LENGTH_SHORT).show()

    }

}
