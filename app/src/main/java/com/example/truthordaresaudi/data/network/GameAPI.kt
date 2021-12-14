package com.example.truthordaresaudi.data.network

import com.example.truthordaresaudi.data.model.GameData
import retrofit2.http.GET
import retrofit2.http.Query

interface GameAPI {
    //we should implement here the four CRUD functions for REST
    @GET("https://61aded2aa7c7f3001786f453.mockapi.io/gameElements")
    suspend fun fetchGameData(): GameData
}