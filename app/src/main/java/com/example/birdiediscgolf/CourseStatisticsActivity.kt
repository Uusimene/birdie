package com.example.birdiediscgolf

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.birdiediscgolf.ui.main.SectionsPagerAdapter
import com.google.gson.Gson

class CourseStatisticsActivity : AppCompatActivity() {

    private lateinit var birdieViewModel: BirdieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_statistics)

        val textView = findViewById<TextView>(R.id.title)

        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)

        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)
        textView.text = courseAndHoles.course.name

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setCourseScores(courseAndHoles)
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
