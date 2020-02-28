package com.example.birdiediscgolf

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileNotFoundException
import java.util.zip.ZipFile

class MainActivity : AppCompatActivity() {

//    private val newWordActivityRequestCode = 1
//    private lateinit var wordViewModel: TestViewModel
    val PICK_ZIP_FILE =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                openFile(Uri.EMPTY)
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

    fun openFile(pickerInitialUri: Uri){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/zip"
        }
        startActivityForResult(intent, PICK_ZIP_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_ZIP_FILE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                //val file = data?.data?.toFile()

                val path = data?.data?.path
                val uri = data?.dataString
                var text : String
                try {
                    val zipfile = ZipFile(uri)
                    val size = zipfile.size()
                    text = "$size.toString() files in $uri"
                } catch (e: FileNotFoundException) {
                    text = "File at $uri couldn't be opened"
                }

                Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
            }
        }
    }

}
