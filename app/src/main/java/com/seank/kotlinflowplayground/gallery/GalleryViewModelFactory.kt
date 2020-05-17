package com.seank.kotlinflowplayground.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seank.kotlinflowplayground.data.CardsRepository
import javax.inject.Inject

class GalleryViewModelFactory constructor(private val cardsRepository: CardsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel(
                cardsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}