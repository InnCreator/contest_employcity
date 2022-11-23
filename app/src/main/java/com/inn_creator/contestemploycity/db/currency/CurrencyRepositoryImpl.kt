package com.inn_creator.contestemploycity.db.currency

import com.inn_creator.contestemploycity.model.DBCurrency
import com.inn_creator.contestemploycity.model.DBCurrencyWithoutFavorite
import com.inn_creator.contestemploycity.model.ValueCurrency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(private val currencyDao: CurrencyDao) {

    suspend fun upsert(vararg dbCurrency: DBCurrencyWithoutFavorite) {
        currencyDao.upsert(*dbCurrency)
    }

    suspend fun getAllCurrency(): List<DBCurrency> {
        return currencyDao.getAllCurrency()
    }

    suspend fun updateCodeToFavorite(dbCurrency: ValueCurrency){
        currencyDao.update(
            DBCurrency(
                code = dbCurrency.code,
                isFavorite = !dbCurrency.isFavorite,
                description = dbCurrency.description
            )
        )
    }
}