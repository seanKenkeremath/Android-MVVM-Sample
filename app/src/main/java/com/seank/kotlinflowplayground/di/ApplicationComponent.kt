package com.seank.kotlinflowplayground.di

import com.seank.kotlinflowplayground.CardsApplication
import com.seank.kotlinflowplayground.main.di.MainModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, MainModule::class])
interface ApplicationComponent {
    fun inject(application: CardsApplication)
}