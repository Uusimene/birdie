package com.example.birdiediscgolf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class MainActivity : AppCompatActivity() {

//    private val newWordActivityRequestCode = 1
//    private lateinit var wordViewModel: TestViewModel
    private lateinit var birdieViewModel: BirdieViewModel
    val PICK_JSON_FILES =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)
        birdieViewModel.gameCount.observe(this, Observer { count ->
            count?.let {
                val gamesButton: Button = findViewById(R.id.gamesButton)
                val buttonText = "Games $it"
                gamesButton.text = buttonText}
        })
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        val adapter = TestListAdapter(this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        wordViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
//        wordViewModel.allWords.observe(this, Observer { words ->
//            words?.let { adapter.setWords(it) }
//        })
//
//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener {
//            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
//            startActivityForResult(intent, newWordActivityRequestCode
//            )
//        }
    }

//    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK){
//            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
//                val word = Word(it)
//                wordViewModel.insert(word)
//            }
//        } else {
//            Toast.makeText(
//                applicationContext,
//                R.string.empty_not_saved,
//                Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.import_export_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.importData -> {
                //Toast.makeText(applicationContext, "Import Data Pressed", Toast.LENGTH_SHORT).show()
                openFile()
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

    private fun openFile(){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "application/json"
        }
        startActivityForResult(intent, PICK_JSON_FILES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_JSON_FILES)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(applicationContext, "No permission", Toast.LENGTH_LONG).show()
                    return
                } else {
                    var uri: Uri? = null

                    data?.let {
                        val paths : MutableList<Uri> = ArrayList()
                        val clipData = it.clipData
                        val clipSize = clipData?.itemCount
                        if (clipSize != null && clipSize > 0)
                        {
                            for (i in 0 until clipSize){
                                uri = clipData.getItemAt(i)?.uri
                                if (uri != null)
                                {
                                    paths.add(uri as Uri)
                                }

                            }
                        }

                        var text: String
                        var players: JSONArray
                        var courses: JSONArray
                        var holes: JSONArray
                        var games: JSONArray
                        var gamePlayers: JSONArray
                        var gameHoles: JSONArray
                        var scores: JSONArray

                        for (i in 0 until paths.size)
                        {
                            try {
                                if (uri == null)
                                {
                                    return
                                }
                                val inputStream = contentResolver.openInputStream(paths[i])
                                if (inputStream == null)
                                {
                                    Toast.makeText(applicationContext, "Input stream null at ${paths[i]}", Toast.LENGTH_LONG).show()
                                    return
                                }
                                val reader = BufferedReader(InputStreamReader(inputStream))
                                val stringBuilder = StringBuilder()

                                var currentLine = reader.readLine()

                                while (currentLine != null)
                                {
                                    stringBuilder.append(currentLine + "\n")
                                    currentLine = reader.readLine()
                                }
                                inputStream.close()
                                text = stringBuilder.toString()
                                val jsonObject = JSONObject(text)
                                val keys = jsonObject.keys()
                                keys.forEach {
                                    when (it) {
                                        "players" -> {
                                            players = jsonObject.getJSONArray(it)
                                            birdieViewModel.importPlayers(players)
                                        }
                                        "courses" -> {
                                            courses = jsonObject.getJSONArray(it)
                                            birdieViewModel.importCourses(courses)
                                        }
                                        "holes" -> {
                                            holes = jsonObject.getJSONArray(it)
                                            birdieViewModel.importHoles(holes)
                                        }
                                        "games" -> {
                                            games = jsonObject.getJSONArray(it)
                                            birdieViewModel.importGames(games)
                                        }
                                        "gamePlayers" -> {
                                            gamePlayers = jsonObject.getJSONArray(it)
                                            birdieViewModel.importGamePlayers(gamePlayers)
                                        }
                                        "gameHoles" -> {
                                            gameHoles = jsonObject.getJSONArray(it)
                                            birdieViewModel.importGameHoles(gameHoles)
                                        }
                                        "scores" -> {
                                            scores = jsonObject.getJSONArray(it)
                                            birdieViewModel.importScores(scores)
                                        }
                                    }
                                }

                            } catch (e: FileNotFoundException) {
                                text = "File at ${paths[i]} couldn't be opened"
                            }
                        }

                        Toast.makeText(applicationContext, "ok", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
