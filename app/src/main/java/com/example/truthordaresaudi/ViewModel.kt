package com.example.truthordaresaudi

import androidx.lifecycle.MutableLiveData
import com.example.truthordaresaudi.data.network.GameRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {
    private val myRepo = GameRepo()

    fun UserInfo(): MutableLiveData<Users> {
        var user = MutableLiveData<Users>()

        viewModelScope.launch {
            myRepo.retrieveUserData()
            user = myRepo.user
        }
        return user
    }
}