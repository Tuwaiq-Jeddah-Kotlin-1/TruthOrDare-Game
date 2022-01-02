package com.example.truthordaresaudi.data.repo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.UserSuggestions
import com.example.truthordaresaudi.data.network.GameBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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

    suspend fun updateUser(username: String) = withContext(Dispatchers.IO) {
        val db = FirebaseFirestore.getInstance()
        val uId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Users").document(uId).update("fullName",username).addOnCompleteListener {
            it.addOnSuccessListener {
                Log.d("updateUser", "success")
            }
            it.addOnFailureListener { exception ->
                Log.d("updateUser", exception.message.toString())
            }

        }
    }
/*
    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }*/

}