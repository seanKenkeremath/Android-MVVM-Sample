package com.seank.kotlinflowplayground.gallery.di

import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.gallery.GalleryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class GalleryModule {
    // This could just use constructor injection, but wanted to show an example
    @Provides
    fun providesGalleryViewModelFactory(cardsRepository: CardsRepository): GalleryViewModelFactory {
        return GalleryViewModelFactory(cardsRepository)
    }

    // Provide feature-specific dependencies here
}