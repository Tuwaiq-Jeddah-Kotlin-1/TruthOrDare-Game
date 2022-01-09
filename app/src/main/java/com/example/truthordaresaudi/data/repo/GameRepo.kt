package com.example.truthordaresaudi.data.repo

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import java.util.*

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

    fun setLocal(activity: Activity, languageCode: String){
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
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

