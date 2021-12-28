package com.example.truthordaresaudi.data.repo

import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.UserSuggestions
import com.example.truthordaresaudi.data.network.GameBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepo {
    private val api = GameBuilder.gameAPI
//    val uid = FirebaseAuth.getInstance().currentUser!!.uid




    suspend fun bringGameData(): List<GameData> = withContext(Dispatchers.IO) {
        api.bringGameData()
    }

    suspend fun userRequests(gameValue: UserSuggestions) = withContext(Dispatchers.IO) {
        api.userRequests(gameValue)
    }

    suspend fun updateUser(name: String) = withContext(Dispatchers.IO) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("Users").document(uid).update("username",name).addOnCompleteListener {
//            it.addOnSuccessListener {
//                Log.d("updateUser", "success")
//            }
//            it.addOnFailureListener { exception ->
//                Log.d("updateUser", exception.message.toString())
//            }
//        }
    }
}