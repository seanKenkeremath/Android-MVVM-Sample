package com.seank.kotlinflowplayground.di

import com.seank.kotlinflowplayground.CardsApplication
import com.seank.kotlinflowplayground.gallery.di.GalleryModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, GalleryModule::class])
interface ApplicationComponent {
    fun inject(application: CardsApplication)
}