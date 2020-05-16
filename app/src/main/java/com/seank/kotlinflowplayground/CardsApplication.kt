package com.seank.kotlinflowplayground

import android.app.Activity
import android.app.Application
import com.seank.kotlinflowplayground.di.ApplicationComponent
import com.seank.kotlinflowplayground.di.ApplicationModule
import com.seank.kotlinflowplayground.di.DaggerApplicationComponent

class CardsApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}

/**
 * This extension method allows us to easily access our Application subclass and
 * Dagger component without casting
 */
val Activity.app: CardsApplication
    get() = application as CardsApplication