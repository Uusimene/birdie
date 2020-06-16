package com.example.birdiediscgolf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.birdiediscgolf.ui.main.GameSectionsPagerAdapter
import com.example.birdiediscgolf.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val textView = findViewById<TextView>(R.id.title)

        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)

        textView.text = courseAndHoles.course.name

        val sectionsPagerAdapter = GameSectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setCourseScores(courseAndHoles)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}
