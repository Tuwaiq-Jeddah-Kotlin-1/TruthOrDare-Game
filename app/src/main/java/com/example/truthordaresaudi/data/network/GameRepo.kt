package com.example.truthordaresaudi.data.network

import androidx.lifecycle.MutableLiveData
import com.example.truthordaresaudi.data.model.Users
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.GameDataList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepo {
    val api = GameBuilder.gameAPI

    private var db = FirebaseFirestore.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var user:MutableLiveData<Users> = MutableLiveData<Users>()



    suspend fun fetchGameData(): GameDataList = withContext(Dispatchers.IO) {
        api.fetchGameData()
    }
    suspend fun fetchGameDataList(): GameDataList = withContext(Dispatchers.IO) {
        api.fetchGameDataList()
    }



    suspend fun retrieveUserData() { withContext(Dispatchers.IO) {
        db.collection("Users").document("$uid").get().addOnCompleteListener() {
            it.addOnSuccessListener { snapshot ->
                // snapshot?.let { docSnap ->
                if (snapshot != null) {
                    val user2 = snapshot.toObject(Users::class.java)
                    user.value=user2!!
                }

            }
        }
    }
    }
}