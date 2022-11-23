package com.inn_creator.contestemploycity.api

import android.util.Log
import com.inn_creator.contestemploycity.model.Symbols
import com.inn_creator.contestemploycity.model.ValueCurrency
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiExchangeRates @Inject constructor() : Currencies {

    private var service: RetrofitCurrencies

    companion object {
        private const val ENDPOINT = "https://api.exchangerate.host/"
    }

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ENDPOINT)
            .build()

        service = retrofit.create(RetrofitCurrencies::class.java)

    }

    override suspend fun getListSymbols(): List<Symbols> {
        val call = service.listCurrency()

        try {
            val res = call.awaitResponse()
            println(res)
            println(res.body())
            val body = res.body()
            if (body != null) {
                return body.symbols.map { it.value }.toList()
            }
        } catch (e: Throwable) {
            Log.e("error", "Error", e)
            throw Throwable()
        }

        return listOf()

    }

    override suspend fun getLatestCurrencies(code: String): List<ValueCurrency> {
        val call = service.listLatest(code)

        try {
            val res = call.awaitResponse()
            println(res)
            println(res.body())
            val body = res.body()
            if (body != null) {
                val list = mutableListOf<ValueCurrency>()
                body.rates.forEach {
                    list.add(
                        ValueCurrency(
                            date = body.date,
                            code = it.key,
                            value = it.value,
                            valueString = String.format("%.6f", it.value)
                        )
                    )
                }
                return list
            }
        } catch (e: Throwable) {
            Log.e("error", "Error", e)
            throw Throwable()
        }

        return listOf()

    }

}

data class ExchangesLatest(
    val motd: Motd,
    val success: Boolean,
    val base: String,
    val date: String,
    val rates: Map<String, Float>,  // Code/Value
)

data class ExchangeSymbols(
    val motd: Motd,
    val success: Boolean,
    val symbols: Map<String, Symbols>,
)

data class Motd(
    val msg: String,
    val url: String,
)
