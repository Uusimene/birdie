package com.example.birdiediscgolf

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.birdiediscgolf.ui.main.SectionsPagerAdapter
import com.google.gson.Gson

class CourseStatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_statistics)

        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setTabTitles(courseAndHoles.holes.size)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, courseAndHoles.course.name, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}