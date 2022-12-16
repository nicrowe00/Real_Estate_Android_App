package org.wit.estate.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import org.wit.estate.databinding.ActivitySettingsBinding
import org.wit.estate.R

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences
    private var switchCheck: Boolean = false


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)


            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            sharedPreferences = getSharedPreferences("org.wit.estate", MODE_PRIVATE)
            switchCheck = sharedPreferences.getBoolean("switch_status", false)
            if(switchCheck){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }


            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, EstateListActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }