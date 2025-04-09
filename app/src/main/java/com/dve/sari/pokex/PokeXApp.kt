package com.dve.sari.pokex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class PokeXApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}