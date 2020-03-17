package com.example.birdiediscgolf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val textView = findViewById<TextView>(R.id.textView)

        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)

        textView.text = courseAndHoles.course.name
    }
}
