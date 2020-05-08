package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.domain.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getCards() : Flow<List<Card>>
}