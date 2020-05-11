package com.seank.kotlinflowplayground.di

import com.seank.kotlinflowplayground.data.CardsService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class FlavorizedApplicationModule {
    @Provides
    @Singleton
    fun providesCardsService(okHttpClient: OkHttpClient, moshi: Moshi): CardsService {
        return Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build().create(CardsService::class.java)
    }
}