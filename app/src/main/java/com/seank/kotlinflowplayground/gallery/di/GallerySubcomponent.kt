package com.seank.kotlinflowplayground.gallery.di

import com.seank.kotlinflowplayground.gallery.GalleryActivity
import dagger.Subcomponent

@Subcomponent(modules = [GalleryModule::class])
interface GallerySubcomponent {
    fun inject(galleryActivity: GalleryActivity)
}