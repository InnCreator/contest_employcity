package com.inn_creator.contestemploycity.api

import com.inn_creator.contestemploycity.model.Symbols
import com.inn_creator.contestemploycity.model.ValueCurrency

interface Currencies {
    suspend fun getListSymbols() : List<Symbols>
    suspend fun getLatestCurrencies(code: String) : List<ValueCurrency>
}