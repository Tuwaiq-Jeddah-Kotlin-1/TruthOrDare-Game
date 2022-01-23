package com.example.truthordaresaudi

import android.app.Activity
import android.app.Application
import android.util.Log
import com.example.truthordaresaudi.data.repo.GameRepo
import com.example.truthordaresaudi.data.model.GameData
import com.example.truthordaresaudi.data.model.UserSuggestions
import android.content.res.Configuration
import kotlinx.coroutines.launch
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.truthordaresaudi.data.repo.DataStoreRepo
import kotlinx.coroutines.Dispatchers
import java.util.*


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val myRepo = GameRepo()
    private val dataStoreRepo = DataStoreRepo(application)
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
                Toast.makeText(context, "No internet connection :(", Toast.LENGTH_LONG).show()
            }
        }
        return tORd
    }

    fun userRequests(gameVal: UserSuggestions) {
        viewModelScope.launch {
            try {
                myRepo.userRequests(gameVal)
            } catch (e: Throwable) {
                Log.e("Mock API...", "Problem AT user requests ${e.localizedMessage}")
            }
        }
    }

    fun checkInternetConnection(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }


    fun updateUser(username: String) {
        viewModelScope.launch {
            myRepo.updateUser(username)
        }
    }

    fun setLocale(activity: Activity, languageCode: String, viewLifecycleOwner: LifecycleOwner) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

    }


    fun loginFirebase(
        email: String,
        password: String,
        isRemembered: Boolean,
        lifecycleOwner: LifecycleOwner
    ): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        myRepo.loginFirebase(email, password).observe(lifecycleOwner, Observer {
            if (it) {
                saveRememberMe(isRemembered)
                loadUserInfo()
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        })
        return loginResult
    }

    fun registerFirebase(
        email: String,
        password: String,
        isRemembered: Boolean,
        registerName: String,
        lifecycleOwner: LifecycleOwner
    ): LiveData<Boolean> {
        val registerResult = MutableLiveData<Boolean>()
        myRepo.registerFirebase(email, password).observe(lifecycleOwner, Observer {
            if (it) {
                saveRememberMe(isRemembered)
                loadUserInfo()
                myRepo.signUpNewUser(email, registerName)
                Log.e("true", "Result true")
                registerResult.postValue(true)
            } else {
                Log.e("false", "Result faLse")
                registerResult.postValue(false)
            }
        })
        return registerResult
    }

    fun userInfo() = myRepo.userInfo

    fun loadUserInfo() {
        myRepo.getUserInfo()
    }

    fun resetPassword(
        email: String,
        lifecycleOwner: LifecycleOwner
    ): LiveData<Boolean> {
        val resetResult = MutableLiveData<Boolean>()
        myRepo.resetPassword(email).observe(lifecycleOwner, Observer {
            if (it) {
                resetResult.postValue(true)
            } else {
                resetResult.postValue(false)
            }
        })
        return resetResult

    }


}