package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.data.model.ApiCardsResponse
import retrofit2.http.GET

interface CardsService {
    @GET("/v1/cards")
    suspend fun getCards() : ApiCardsResponse
}