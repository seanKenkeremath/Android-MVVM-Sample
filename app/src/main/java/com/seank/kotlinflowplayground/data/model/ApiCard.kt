package com.seank.kotlinflowplayground.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCard(
    @Json(name="name") val name: String,
    @Json(name="imageUrl") val imageUrl: String
)