package org.wit.estate.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstateModel(var id: Long = 0,
                          var name: String = "",
                          var phonenumber: Int = 0,
                          var image: Uri = Uri.EMPTY,
                          var type: String = "",
                          var address: String = "",
                          var city: String = "",
                          var county: String = "",
                          var eircode: String = "",
                          var estimated: Int = 0,
                          var residents: Int = 0,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable