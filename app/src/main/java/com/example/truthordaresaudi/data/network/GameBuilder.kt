package com.example.truthordaresaudi.data.network

import com.example.truthordaresaudi.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameBuilder {
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //local API to call global API
    val gameAPI: GameAPI = retrofit().create(GameAPI::class.java)
}