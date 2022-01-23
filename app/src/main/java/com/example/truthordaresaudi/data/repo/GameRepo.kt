package com.example.truthordaresaudi.data.repo


import android.util.Log
import androidx.lifecycle.*
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.UserSuggestions
import com.example.truthordaresaudi.data.model.Users
import com.example.truthordaresaudi.data.network.GameBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*

class GameRepo {
    private val api = GameBuilder.gameAPI
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    var userInfo = Users("", "", "")



    suspend fun bringGameData(): List<GameData> = withContext(Dispatchers.IO) {
        api.bringGameData()
    }

    suspend fun userRequests(gameValue: UserSuggestions) = withContext(Dispatchers.IO) {
        api.userRequests(gameValue)
    }

    suspend fun updateUser(username: String) = withContext(Dispatchers.IO) {
        val db = FirebaseFirestore.getInstance()
        val uId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("Users").document(uId).update("fullName", username).addOnCompleteListener {
            it.addOnSuccessListener {
                Log.d("updateUser", "success")
            }
            it.addOnFailureListener { exception ->
                Log.d("updateUser", exception.message.toString())
            }
        }
    }

     fun loginFirebase(email: String, password: String): MutableLiveData<Boolean>{
         val loginResult = MutableLiveData<Boolean>()
         auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { operation ->
                    loginResult.postValue(operation.isSuccessful)
                }
        return loginResult
     }

    fun registerFirebase(email: String, password: String): MutableLiveData<Boolean>{
         val registerResult = MutableLiveData<Boolean>()
         auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { operation ->
                    registerResult.postValue(operation.isSuccessful)
                }
        return registerResult
     }

    fun signUpNewUser(emailU: String, nameU: String) {
            val uId = FirebaseAuth.getInstance().currentUser?.uid!!
            val user = Users(email = emailU,fullName = nameU)
            try {
                db.collection("Users").document("$uId").set(user)
            } catch (e: Exception) {
                Log.e("Exception", e.localizedMessage)
            }
    }

    fun getUserInfo(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            db.collection("Users")
                .document(uid!!)
                .get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.toObject<Users>()
                    if (user != null) {
                        userInfo = user
                    }
                }
        }
    }

    fun resetPassword(email: String): MutableLiveData<Boolean> {
        val resetResult = MutableLiveData<Boolean>()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { operation ->
                resetResult.postValue(operation.isSuccessful)
            }
        return resetResult
    }

}

