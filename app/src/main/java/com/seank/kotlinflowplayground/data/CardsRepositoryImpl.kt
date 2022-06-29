package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.domain.Card
import com.seank.kotlinflowplayground.flow.DefaultDispatcherProvider
import com.seank.kotlinflowplayground.flow.DispatcherProvider
import kotlinx.coroutines.flow.*

class CardsRepositoryImpl(
    private val cardsService: CardsService,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : CardsRepository {
    override fun getCards(): Flow<Result<List<Card>>> {
        return flow {
            emit(cardsService.getCards("LEA").apiCards)
        }.map {
            val domainCardsList = mutableListOf<Card>()
            for (apiCard in it) {
                domainCardsList.add(Card(apiCard))
            }
            Result.success(domainCardsList)
        }
            .catch {
                //TODO: domain abstractions for exceptions
                emit(Result.failure(it))
            }
            .flowOn(dispatcherProvider.io())
    }
}