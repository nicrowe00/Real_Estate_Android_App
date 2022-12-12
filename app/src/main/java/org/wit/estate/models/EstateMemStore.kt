package org.wit.estate.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class EstateMemStore : EstateStore {

    val estates = ArrayList<EstateModel>()

    override fun findAll(): List<EstateModel> {
        return estates
    }

    override fun create(estate: EstateModel) {
        estate.id = getId()
        estates.add(estate)
        logAll()
    }

    override fun update(estate: EstateModel) {
        var foundEstate: EstateModel? = estates.find { p -> p.id == estate.id }
        if (foundEstate != null) {
            foundEstate.name = estate.name
            foundEstate.phonenumber = estate.phonenumber
            foundEstate.type = estate.type
            foundEstate.address = estate.address
            foundEstate.city = estate.city
            foundEstate.county = estate.county
            foundEstate.eircode = estate.eircode
            foundEstate.residents = estate.residents
            foundEstate.image = estate.image
            foundEstate.lat = estate.lat
            foundEstate.lng = estate.lng
            foundEstate.zoom = estate.zoom
            logAll()
        }
    }

    private fun logAll() {
        estates.forEach { i("$it") }
    }

    override fun delete(estate: EstateModel) {
        estates.remove(estate)
    }
}