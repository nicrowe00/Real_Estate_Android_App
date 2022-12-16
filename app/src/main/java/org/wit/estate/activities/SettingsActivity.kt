package org.wit.estate.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import org.wit.estate.R
import org.wit.estate.databinding.ActivitySettingsBinding
import org.wit.estate.main.MainApp

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    lateinit var app: MainApp
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private var switchCheck: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val darkTheme = findViewById<SwitchCompat>(R.id.switchtheme)
        sharedPreferences = getSharedPreferences("org.wit.estate", MODE_PRIVATE)
        switchCheck = sharedPreferences.getBoolean("switch_status", false)
        darkTheme.isChecked = switchCheck

        darkTheme.setOnClickListener() {
            if (darkTheme.isChecked) {
                editor = getSharedPreferences("org.wit.estate", 0).edit()
                editor.putBoolean("switch_status", true)
                editor.commit()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor = getSharedPreferences("org.wit.estate", MODE_PRIVATE).edit()
                editor.putBoolean("switch_status", false)
                editor.commit()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.style.Theme_estate
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        sharedPreferences = getSharedPreferences("org.wit.estate", MODE_PRIVATE)
        switchCheck = sharedPreferences.getBoolean("switch_status", false)
        return if(switchCheck){
            menuInflater.inflate(R.menu.dark_menu_back, menu)
            return super.onCreateOptionsMenu(menu)
        } else {
            menuInflater.inflate(R.menu.menu_back, menu)
            return super.onCreateOptionsMenu(menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_back -> {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}