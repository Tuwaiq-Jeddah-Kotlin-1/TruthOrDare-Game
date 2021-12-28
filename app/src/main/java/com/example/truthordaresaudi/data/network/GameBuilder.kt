package com.example.truthordaresaudi.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameBuilder {
    private const val BASE_URL = "https://61aded2aa7c7f3001786f453.mockapi.io/"
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //local API to call global API
    val gameAPI: GameAPI = retrofit().create(GameAPI::class.java)
}