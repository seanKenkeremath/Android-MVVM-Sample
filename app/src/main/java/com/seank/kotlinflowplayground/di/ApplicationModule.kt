package com.seank.kotlinflowplayground.di

import android.app.Application
import android.content.Context
import com.seank.kotlinflowplayground.data.CardsRepository
import com.seank.kotlinflowplayground.data.CardsRepositoryImpl
import com.seank.kotlinflowplayground.data.CardsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesCardsService(moshi: Moshi) : CardsService {
        val okHttpClient = OkHttpClient
            .Builder()
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(CardsService::class.java)
    }

    @Provides
    fun providesCardsRepository(cardsService: CardsService): CardsRepository {
        return CardsRepositoryImpl(cardsService)
    }
}