package com.seank.kotlinflowplayground.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCardsResponse(
    @Json(name="cards") val apiCards: List<ApiCard>
)