package com.seank.kotlinflowplayground.gallery.di

import com.seank.kotlinflowplayground.gallery.GalleryActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [GallerySubcomponent::class])
abstract class GalleryModule {
    @Binds
    @IntoMap
    @ClassKey(GalleryActivity::class)
    internal abstract fun bindMainAndroidInjectorFactory(factory: GallerySubcomponent.Factory): AndroidInjector.Factory<*>
}