package com.example.truthordaresaudi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.truthordaresaudi.data.network.GameRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.Users
import com.example.truthordaresaudi.data.model.UserSuggestions
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {
    private val myRepo = GameRepo()

    fun dataList(): LiveData<List<GameData>> {
        val tORd = MutableLiveData<List<GameData>>()
        viewModelScope.launch {
            try {
                tORd.postValue(myRepo.bringGameData())

            }catch (e: Throwable){
                Log.e("Mock API...","Truth or Dare data Problem ${e.localizedMessage}")
            }
        }
        return tORd
    }

    fun userRequests(gameVal : UserSuggestions){
        viewModelScope.launch {
            try {
             myRepo.userRequests(gameVal)
            }catch (e: Throwable){
                Log.e("Mock API...","Truth or Dare data Problem user requests ${e.localizedMessage}")
            }
        }
    }

    //From firebase
    fun userInfo(): MutableLiveData<Users> {
        var user = MutableLiveData<Users>()

        viewModelScope.launch {
            myRepo.retrieveUserData()
            user = myRepo.user
        }
        return user
    }
}