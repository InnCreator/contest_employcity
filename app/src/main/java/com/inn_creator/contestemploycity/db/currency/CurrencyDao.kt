package com.inn_creator.contestemploycity.db.currency

import androidx.room.*
import com.inn_creator.contestemploycity.model.DBCurrency
import com.inn_creator.contestemploycity.model.DBCurrencyWithoutFavorite


@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: DBCurrency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg currencies: DBCurrency)

    @Delete
    suspend fun delete(currency: DBCurrency)

    @Update
    suspend fun update(currency: DBCurrency)

    @Update
    suspend fun update(vararg currencies: DBCurrency)

    @Upsert(entity = DBCurrency::class)
    suspend fun upsert(currency: DBCurrencyWithoutFavorite)

    @Upsert(entity = DBCurrency::class)
    suspend fun upsert(vararg currencies: DBCurrencyWithoutFavorite)

    @Query("SELECT * FROM dbcurrency ")
    suspend fun getAllCurrency(): List<DBCurrency>

}