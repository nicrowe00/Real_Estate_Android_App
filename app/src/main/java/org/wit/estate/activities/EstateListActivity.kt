package org.wit.estate.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.estate.activities.EstateMapsActivity
import org.wit.estate.R
import org.wit.estate.adapters.EstateAdapter
import org.wit.estate.adapters.EstateListener
import org.wit.estate.databinding.ActivityEstateListBinding
import org.wit.estate.main.MainApp
import org.wit.estate.models.EstateModel

class EstateListActivity : AppCompatActivity(), EstateListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityEstateListBinding
    private var position: Int = 0
    private lateinit var sharedPreferences : SharedPreferences
    private var switchCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EstateAdapter(app.estates.findAll(),this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        sharedPreferences = getSharedPreferences("org.wit.estate", MODE_PRIVATE)
        switchCheck = sharedPreferences.getBoolean("switch_status", false)
        return if(switchCheck){
            menuInflater.inflate(R.menu.dark_menu_main, menu)
            return true
        } else {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, EstateActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, EstateMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
            R.id.item_settings -> {
                val launcherIntent = Intent(this, SettingsActivity::class.java)
                settingsIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

     private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.estates.findAll().size)
            }
        }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }

    private val settingsIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {  }

    override fun onestateClick(estate: EstateModel, pos : Int) {
        val launcherIntent = Intent(this, EstateActivity::class.java)
        launcherIntent.putExtra("estate_edit", estate)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.estates.findAll().size)
            }
            else // Deleting
              if (it.resultCode == 99)
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
}
