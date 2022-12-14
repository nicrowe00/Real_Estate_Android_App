package org.wit.estate.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.estate.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "estates.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<EstateModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class EstateJSONStore(private val context: Context) : EstateStore {
    var estates = mutableListOf<EstateModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<EstateModel> {
        logAll()
        return estates
    }

    override fun create(estate: EstateModel) {
        estate.id = generateRandomId()
        estates.add(estate)
        serialize()
    }

    override fun update(estate: EstateModel) {
        val estatesList = findAll() as ArrayList<EstateModel>
        var foundEstate: EstateModel? = estatesList.find { p -> p.id == estate.id }
        if (foundEstate != null) {
            foundEstate.name = estate.name
            foundEstate.phonenumber = estate.phonenumber
            foundEstate.type = estate.type
            foundEstate.address = estate.address
            foundEstate.city = estate.city
            foundEstate.county = estate.county
            foundEstate.eircode = estate.eircode
            foundEstate.estimated = estate.estimated
            foundEstate.residents = estate.residents
            foundEstate.image = estate.image
            foundEstate.lat = estate.lat
            foundEstate.lng = estate.lng
            foundEstate.zoom = estate.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(estates, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        estates = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(estate: EstateModel) {
        estates.remove(estate)
        serialize()
    }

    fun deleteAll(){
        estates.removeAll(estates)
        serialize()
    }

    private fun logAll() {
        estates.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}