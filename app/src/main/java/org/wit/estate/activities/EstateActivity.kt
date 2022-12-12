package org.wit.estate.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.estate.R
import org.wit.estate.databinding.ActivityEstateBinding
import org.wit.estate.main.MainApp
import org.wit.estate.models.Location
import org.wit.estate.models.EstateModel
import org.wit.estate.showImagePicker
import timber.log.Timber.i

class EstateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstateBinding
    var estate = EstateModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edit = false

        binding = ActivityEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("PEstate Activity started...")

        if (intent.hasExtra("estate_edit")) {
            edit = true
            estate = intent.extras?.getParcelable("estate_edit")!!
            binding.name.setText(estate.name)
            binding.phonenumber.setText(Integer.toString(estate.phonenumber))
            binding.type.setText(estate.type)
            binding.address.setText(estate.address)
            binding.city.setText(estate.city)
            binding.county.setText(estate.county)
            binding.eircode.setText(estate.eircode)
            binding.estimated.setText(Integer.toString(estate.estimated))
            binding.residents.setText(Integer.toString(estate.residents))
            binding.btnAdd.setText(R.string.save_estate)
            Picasso.get()
                .load(estate.image)
                .into(binding.estateImage)
            if (estate.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_estate_image)
            }

        }

        binding.btnAdd.setOnClickListener() {
            estate.name = binding.name.text.toString()
            estate.phonenumber = binding.phonenumber.text.toString().toInt()
            estate.type = binding.type.text.toString()
            estate.address = binding.address.text.toString()
            estate.city = binding.city.text.toString()
            estate.county = binding.county.text.toString()
            estate.eircode = binding.eircode.text.toString()
            estate.estimated = binding.estimated.text.toString().toInt()
            estate.residents = binding.residents.text.toString().toInt()
            if (estate.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_estate_title, Snackbar.LENGTH_LONG)
                        .show()
            } else {
                if (edit) {
                    app.estates.update(estate.copy())
                } else {
                    app.estates.create(estate.copy())
                }
            }
            i("add Button Pressed: $estate")

            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher,this)
        }

        binding.estateLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (estate.zoom != 0f) {
                location.lat =  estate.lat
                location.lng = estate.lng
                location.zoom = estate.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_estate, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
            setResult(99)
                app.estates.delete(estate)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            estate.image = image

                            Picasso.get()
                                .load(estate.image)
                                .into(binding.estateImage)
                            binding.chooseImage.setText(R.string.change_estate_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            estate.lat = location.lat
                            estate.lng = location.lng
                            estate.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
