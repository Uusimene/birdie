package com.example.birdiediscgolf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startNewGame(view: View){
        val intent = Intent(this, CourseSelectActivity::class.java)
        startActivity(intent)
    }

    fun viewPastGames(view: View){
        val intent = Intent(this, GamesListActivity::class.java)
        startActivity(intent)
    }

}
