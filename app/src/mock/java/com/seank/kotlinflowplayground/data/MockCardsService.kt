package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.data.model.ApiCard
import com.seank.kotlinflowplayground.data.model.ApiCardsResponse
import kotlinx.coroutines.delay

class MockCardsService : CardsService {
    override suspend fun getCards(setCode: String?): ApiCardsResponse {
        delay(2000) //simulate API call
        return ApiCardsResponse(
            listOf(
                ApiCard("Test Card 1", "https://www.placecage.com/c/200/300"),
                ApiCard("Test Card 2", "https://www.placecage.com/200/300"),
                ApiCard("Test Card 3", "https://www.placecage.com/c/250/350"),
                ApiCard("Test Card 4", "https://www.placecage.com/250/350")
            )
        )
    }
}