package com.inn_creator.contestemploycity.model

data class ValueCurrency(
    val date: String,
    val code: String,
    val description: String? = null,
    val value: Float,
    val valueString: String,
    var isFavorite: Boolean = false
)