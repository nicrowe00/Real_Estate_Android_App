package org.wit.estate.models

interface EstateStore {
    fun findAll(): List<EstateModel>
    fun create(estate: EstateModel)
    fun update(estate: EstateModel)
    fun delete(estate: EstateModel)
}