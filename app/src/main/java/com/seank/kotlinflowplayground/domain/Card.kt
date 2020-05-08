package com.seank.kotlinflowplayground.domain

import com.seank.kotlinflowplayground.data.model.ApiCard

data class Card(
    val name: String,
    val imgUrl: String
) {
    constructor(apiCard: ApiCard) : this(apiCard.name, apiCard.imageUrl)
}