package com.inn_creator.contestemploycity.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inn_creator.contestemploycity.db.currency.CurrencyDao
import com.inn_creator.contestemploycity.model.DBCurrency

@Database(entities = [DBCurrency::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getCurrencyDao(): CurrencyDao
}