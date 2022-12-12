package org.wit.estate.main

import android.app.Application
import org.wit.estate.models.EstateJSONStore
import org.wit.estate.models.EstateStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var estates: EstateStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        estates = EstateJSONStore(applicationContext)
        i("Estate started")
    }
}