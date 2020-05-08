package com.seank.kotlinflowplayground

import android.app.Application
import com.seank.kotlinflowplayground.di.ApplicationModule
import com.seank.kotlinflowplayground.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CardsApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build().inject(this)
    }

}