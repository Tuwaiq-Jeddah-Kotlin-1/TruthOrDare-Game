package com.example.truthordaresaudi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.truthordaresaudi.data.network.GameRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.GameDataList
import com.example.truthordaresaudi.data.model.Users
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {
    private val myRepo = GameRepo()

    fun fetchInterestingList(): LiveData<GameDataList> {
        val tORd = MutableLiveData<GameDataList>()
        viewModelScope.launch {
            try {
                tORd.postValue(myRepo.fetchGameData())
                tORd.postValue(myRepo.fetchGameDataList())

            }catch (e: Throwable){
                Log.e("Mock API...","Truth or Dare data Problem ${e.localizedMessage}")
            }
        }
        return tORd
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