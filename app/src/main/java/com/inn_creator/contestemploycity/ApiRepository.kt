package com.inn_creator.contestemploycity

import com.inn_creator.contestemploycity.api.ApiExchangeRates
import com.inn_creator.contestemploycity.api.Currencies
import com.inn_creator.contestemploycity.db.currency.CurrencyRepositoryImpl
import com.inn_creator.contestemploycity.model.DBCurrencyWithoutFavorite
import com.inn_creator.contestemploycity.model.Symbols
import com.inn_creator.contestemploycity.model.ValueCurrency
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val api: ApiExchangeRates,
    private val repo: CurrencyRepositoryImpl
) : Currencies {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getListSymbols(): List<Symbols> {
        val list = api.getListSymbols()

        GlobalScope.launch {
            async {
                val dbList =
                    list.map { DBCurrencyWithoutFavorite(code = it.code, description = it.name) }
                        .toList()
                repo.upsert(*dbList.toTypedArray())
            }
        }
        return list
    }

    override suspend fun getLatestCurrencies(code: String): List<ValueCurrency> {
        return api.getLatestCurrencies(code)
    }

    suspend fun updateCodeToFavorite(currency: ValueCurrency) {
        repo.updateCodeToFavorite(currency)
    }
}