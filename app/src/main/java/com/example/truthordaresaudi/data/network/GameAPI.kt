package com.example.truthordaresaudi.data.network

import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.GameDataList
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameAPI {
    //we should implement here the four CRUD functions for REST
    @GET("/gameElements")
    suspend fun fetchGameData(): GameDataList

    @GET("/list")
    suspend fun fetchGameDataList(): GameDataList

    @POST("/UserRequests")
    suspend fun userRequests()

}