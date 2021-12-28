package com.example.truthordaresaudi

import android.app.Application
import android.util.Log
import com.example.truthordaresaudi.data.repo.GameRepo
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.Users
import com.example.truthordaresaudi.data.model.UserSuggestions
import kotlinx.coroutines.launch
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.truthordaresaudi.data.repo.DataStoreRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope


class MyViewModel(application: Application): AndroidViewModel(application) {
    private val myRepo = GameRepo()
    private val dataStoreRepo = DataStoreRepo(application)
    var userInfo = Users("", "", "")
    var isFirstTime = true


    val readRememberMe = dataStoreRepo.readRememberMe.asLiveData()
    val readTheme = dataStoreRepo.readTheme.asLiveData()
    val readLanguage = dataStoreRepo.readLanguage.asLiveData()


    fun saveRememberMe(remember: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepo.saveRememberMe(remember)
    }
    fun saveTheme(theme: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepo.saveTheme(theme)
    }
    fun saveLanguage(language: String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepo.saveLanguage(language)
    }

    fun dataList(context: Context): LiveData<List<GameData>> {
        val tORd = MutableLiveData<List<GameData>>()
            viewModelScope.launch {
                try {
                    tORd.postValue(myRepo.bringGameData())

                } catch (e: Throwable) {
                    Log.e("Mock API...", "Problem AT dataList ${e.localizedMessage}")
                }
            }
        return tORd
    }

    fun userRequests(gameVal : UserSuggestions, context: Context, view: View){
            viewModelScope.launch {
                try {
                    myRepo.userRequests(gameVal)
                }catch (e: Throwable){
                    Log.e("Mock API...","Problem AT user requests ${e.localizedMessage}")
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

    fun updateUser(name: String){
        viewModelScope.launch {
            try {
                myRepo.updateUser(name)
            }catch (e: Throwable){
                Log.e("updateUserVM","Error from VM ${e.localizedMessage}")
            }
        }
    }




}