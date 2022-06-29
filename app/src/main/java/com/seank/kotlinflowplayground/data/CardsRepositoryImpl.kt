package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.domain.Card
import com.seank.kotlinflowplayground.domain.GenericException
import com.seank.kotlinflowplayground.domain.NetworkException
import com.seank.kotlinflowplayground.flow.DefaultDispatcherProvider
import com.seank.kotlinflowplayground.flow.DispatcherProvider
import kotlinx.coroutines.flow.*
import java.io.IOException

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
                if (it is IOException) {
                    emit(Result.failure(NetworkException()))
                } else {
                    emit(Result.failure(GenericException()))
                }
            }
            .flowOn(dispatcherProvider.io())
    }
}