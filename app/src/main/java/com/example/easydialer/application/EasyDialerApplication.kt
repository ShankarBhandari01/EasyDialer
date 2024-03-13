package com.example.easydialer.application

import android.app.Application
import androidx.startup.AppInitializer
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.example.easydialer.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import net.danlew.android.joda.JodaTimeInitializer
import timber.log.Timber

@HiltAndroidApp
class EasyDialerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initJodaTime()
        manageCrashingActivity()
    }

    private fun initJodaTime() {
        AppInitializer.getInstance(this)
            .initializeComponent(JodaTimeInitializer::class.java)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun manageCrashingActivity() {
        CaocConfig.Builder.create()
            .logErrorOnRestart(false) //default: true
            .trackActivities(true) //default: false
            .minTimeBetweenCrashesMs(2000) //default: 3000
            .errorActivity(DefaultErrorActivity::class.java)
            .apply()
    }


}