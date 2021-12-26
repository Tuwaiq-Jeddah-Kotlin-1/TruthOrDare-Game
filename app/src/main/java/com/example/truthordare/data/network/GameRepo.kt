package com.example.truthordare.data.network

import com.example.truthordare.data.model.GameData
import com.example.truthordare.data.model.UserSuggestions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepo {
    val api = GameBuilder.gameAPI





    suspend fun bringGameData(): List<GameData> = withContext(Dispatchers.IO) {
        api.bringGameData()
    }

    suspend fun userRequests(gameValue: UserSuggestions) = withContext(Dispatchers.IO) {
        api.userRequests(gameValue)
    }

    suspend fun retrieveUserData() {

    }
}