package com.inn_creator.contestemploycity.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCurrencies {

    @GET("symbols/")
    fun listCurrency(): Call<ExchangeSymbols>
    @GET("/latest")
    fun listLatest(@Query("base") code: String): Call<ExchangesLatest>

}