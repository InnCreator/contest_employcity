package com.inn_creator.contestemploycity.model

import com.squareup.moshi.Json

data class Symbols(
    val code: String,
    @Json(name = "description")
    val name: String,
)
