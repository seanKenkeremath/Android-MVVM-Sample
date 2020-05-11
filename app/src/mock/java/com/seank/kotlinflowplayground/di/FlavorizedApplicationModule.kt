package com.seank.kotlinflowplayground.di

import com.seank.kotlinflowplayground.data.CardsService
import com.seank.kotlinflowplayground.data.MockCardsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FlavorizedApplicationModule {
    @Provides
    @Singleton
    fun providesCardsService(): CardsService {
        return MockCardsService()
    }
}