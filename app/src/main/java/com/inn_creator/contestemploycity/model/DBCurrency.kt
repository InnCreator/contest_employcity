package com.inn_creator.contestemploycity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DBCurrency(
    @PrimaryKey val code: String,
    val description: String?,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean,
)

@Entity
data class DBCurrencyWithoutFavorite(
    @PrimaryKey val code: String,
    val description: String?,
)