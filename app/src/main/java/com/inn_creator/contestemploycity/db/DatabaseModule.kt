package com.inn_creator.contestemploycity.db

import android.content.Context
import androidx.room.Room
import com.inn_creator.contestemploycity.db.currency.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideCurrencyDao(appDataBase: AppDataBase): CurrencyDao {
        return appDataBase.getCurrencyDao()
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "EmplocityDataBase"
        ).build()
    }
}