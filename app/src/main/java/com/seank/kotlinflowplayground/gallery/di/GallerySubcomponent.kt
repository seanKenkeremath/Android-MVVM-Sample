package com.seank.kotlinflowplayground.gallery.di

import com.seank.kotlinflowplayground.gallery.GalleryActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface GallerySubcomponent : AndroidInjector<GalleryActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<GalleryActivity>

}