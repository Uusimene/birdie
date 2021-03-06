package com.example.birdiediscgolf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.birdiediscgolf.ui.main.GameSectionsPagerAdapter
import com.example.birdiediscgolf.ui.main.gameFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import java.util.*

class GameActivity : AppCompatActivity(), gameFragment.OnScoreSelectedListener, ChangeParDialog.DialogListener {

    private lateinit var birdieViewModel: BirdieViewModel

    lateinit var toolbar: Toolbar
    lateinit var game: Game
    lateinit var gamePlayer: GamePlayer
    lateinit var gameHoles: List<GameHole>
    lateinit var scores: List<Score>
    lateinit var course: Course
    lateinit var holes: List<Hole>
    lateinit var player: Player

    lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        birdieViewModel = ViewModelProvider(this).get(BirdieViewModel::class.java)

        val gson = Gson()
        val courseAndHolesJSON = intent.getStringExtra("course")
        val courseAndHoles = gson.fromJson(courseAndHolesJSON, CourseAndHoles::class.java)
        val playersJSON = intent.getStringExtra("players")
        val players = gson.fromJson(playersJSON, Array<Player>::class.java)
        // this may cause issues
        val ownerPlayer = players.filter { it.owner == 1}[0]
        player = ownerPlayer

        val currentTimeStamp = System.currentTimeMillis()
        course = courseAndHoles.course
        holes = courseAndHoles.holes
        game = Game(UUID.randomUUID().toString(), course.uuid, currentTimeStamp, 0, 0, currentTimeStamp, 0)
        gamePlayer = GamePlayer(UUID.randomUUID().toString(), game.uuid, currentTimeStamp, 0, player.uuid)
        val tempGameHoles = mutableListOf<GameHole>()
        holes.forEach {
            val tempHole = GameHole(UUID.randomUUID().toString(), game.uuid, currentTimeStamp, it.hole, it.id, it.par)
            tempGameHoles.add(tempHole)
        }
        gameHoles = tempGameHoles
        val tempScores = mutableListOf<Score>()
        gameHoles.forEach {
            val tempScore = Score(UUID.randomUUID().toString(), currentTimeStamp, it.uuid, gamePlayer.uuid, game.uuid, it.id, -1)
            tempScores.add(tempScore)
        }
        scores = tempScores


        toolbar = findViewById(R.id.toolbar)
        toolbar.title = courseAndHoles.course.name
        setSupportActionBar(toolbar)

        val sectionsPagerAdapter = GameSectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setCourseScores(courseAndHoles)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.endGame -> saveRound()
            R.id.changePar -> openChangeParDialog()
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
        scores[hole - 1].score = score
    }

    override fun getDialogInput(par: Int) {
        super.getDialogInput(par)
        val currentHoleIndex = tabs.selectedTabPosition
        val hole = holes[currentHoleIndex]
        birdieViewModel.updateHolePar(hole.uuid, par)
    }

    private fun saveRound() {
        game.endedAt = System.currentTimeMillis()
        birdieViewModel.insertGame(game)
        birdieViewModel.insertGameHoles(gameHoles)
        birdieViewModel.insertGamePlayer(gamePlayer)
        birdieViewModel.insertScores(scores)
    }

    private fun openChangeParDialog() {
        val changeParDialog = ChangeParDialog()
        changeParDialog.show(supportFragmentManager, "diggoo")
    }

}
