package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.domain.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CardsRepositoryImpl(private val cardsService: CardsService) : CardsRepository {
    @ExperimentalCoroutinesApi
    override fun getCards(): Flow<List<Card>> {
        return flow {
            emit(cardsService.getCards().apiCards)
        }.map {
            val domainCardsList = mutableListOf<Card>()
            for (apiCard in it) {
                domainCardsList.add(Card(apiCard))
            }
            domainCardsList
        }
            .flowOn(Dispatchers.IO)
    }
}