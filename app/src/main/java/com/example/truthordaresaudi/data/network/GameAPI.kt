package com.example.truthordaresaudi.data.network

import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.UserSuggestions
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameAPI {
    //we should implement here the four CRUD functions for REST
    @GET("/gameElements")
    suspend fun bringGameData():  List<GameData>

    @POST("/UserRequests")
    suspend fun userRequests(@Body gameValue : UserSuggestions)

}