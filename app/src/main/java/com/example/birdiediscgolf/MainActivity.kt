package com.example.birdiediscgolf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.import_export_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.importData -> {
                Toast.makeText(applicationContext, "Import Data Pressed", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.exportData -> {
                Toast.makeText(applicationContext, "Export Data Pressed", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                Toast.makeText(applicationContext, "How do I shot web", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
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
