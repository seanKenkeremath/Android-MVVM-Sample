package com.seank.kotlinflowplayground.data

import com.seank.kotlinflowplayground.data.model.ApiCardsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsService {
    @GET("/v1/cards")
    suspend fun getCards(@Query("set") setCode: String? = null) : ApiCardsResponse
}