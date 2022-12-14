package org.wit.estate.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.estate.R
import org.wit.estate.databinding.ActivityEstateMapsBinding
import org.wit.estate.databinding.ContentEstateMapsBinding
import org.wit.estate.main.MainApp

class EstateMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityEstateMapsBinding
    private lateinit var contentBinding: ContentEstateMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp
    private lateinit var sharedPreferences : SharedPreferences
    private var switchCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding = ActivityEstateMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentEstateMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        app = application as MainApp
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        app.estates.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }
    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }
    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        sharedPreferences = getSharedPreferences("org.wit.estate", MODE_PRIVATE)
        switchCheck = sharedPreferences.getBoolean("switch_status", false)
        return if(switchCheck){
            menuInflater.inflate(R.menu.dark_menu_back, menu)
            return true
        } else {
            menuInflater.inflate(R.menu.menu_back, menu)
            return true
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