package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.domain.Card
import com.seank.kotlinflowplayground.flow.DefaultDispatcherProvider
import com.seank.kotlinflowplayground.flow.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CardsRepositoryImpl(private val cardsService: CardsService, private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()) : CardsRepository {
    override fun getCards(): Flow<List<Card>> {
        return flow {
            emit(cardsService.getCards("LEA").apiCards)
        }.map {
            val domainCardsList = mutableListOf<Card>()
            for (apiCard in it) {
                domainCardsList.add(Card(apiCard))
            }
            domainCardsList
        }
            .flowOn(dispatcherProvider.io())
    }
}