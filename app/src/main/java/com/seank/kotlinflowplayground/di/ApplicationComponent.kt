package com.seank.kotlinflowplayground.di

import com.seank.kotlinflowplayground.CardsApplication
import com.seank.kotlinflowplayground.gallery.di.GalleryModule
import com.seank.kotlinflowplayground.gallery.di.GallerySubcomponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: CardsApplication)
    fun gallerySubcomponent(galleryModule: GalleryModule): GallerySubcomponent
}