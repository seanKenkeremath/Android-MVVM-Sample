package com.seank.kotlinflowplayground.main.di

import com.seank.kotlinflowplayground.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainSubcomponent::class])
abstract class MainModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun bindMainAndroidInjectorFactory(factory: MainSubcomponent.Factory): AndroidInjector.Factory<*>
}