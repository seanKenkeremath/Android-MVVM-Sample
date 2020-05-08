package com.seank.kotlinflowplayground.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seank.kotlinflowplayground.data.CardsRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val cardsRepository: CardsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                cardsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}