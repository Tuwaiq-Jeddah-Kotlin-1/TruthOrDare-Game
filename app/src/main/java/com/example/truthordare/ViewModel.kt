package com.example.truthordare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.truthordare.data.network.GameRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truthordare.data.model.GameData
import com.example.truthordare.data.model.Users
import com.example.truthordare.data.model.UserSuggestions
import kotlinx.coroutines.launch
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers


class ViewModel: ViewModel() {
    private val myRepo = GameRepo()

    fun dataList(context: Context): LiveData<List<GameData>> {
        val tORd = MutableLiveData<List<GameData>>()
            viewModelScope.launch {
                try {
                    tORd.postValue(myRepo.bringGameData())

                } catch (e: Throwable) {
                    Log.e("Mock API...", "Truth or Dare data Problem ${e.localizedMessage}")
                }
            }
        return tORd
    }

    fun userRequests(gameVal : UserSuggestions, context: Context, view: View){
            viewModelScope.launch {
                try {
                    myRepo.userRequests(gameVal)
                }catch (e: Throwable){
                    Log.e("Mock API...","Truth or Dare data Problem user requests ${e.localizedMessage}")
                }
            }
    }

    fun checkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
    private var db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
     var user: MutableLiveData<Users> = MutableLiveData<Users>()
    //From firebase
     fun userInfo(): MutableLiveData<Users> {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("Users")
                .document(uid!!)
                .get()
                .addOnSuccessListener { snapshot ->
//                    if (snapshot != null) {
                    user.value = snapshot.toObject<Users>()
//                    }
                }
        }
        return user
       /* var user = MutableLiveData<Users>()
        viewModelScope.launch {
            myRepo.retrieveUserData()
            user.postValue()
        }
        return user*/
    }
}