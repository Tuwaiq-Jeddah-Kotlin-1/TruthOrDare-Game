package com.example.truthordaresaudi.data.repo

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.truthordaresaudi.PREF_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)
class DataStoreRepo(private val context: Context) {

    private object MyKeys{
        val rememberMe = booleanPreferencesKey(name = "remember_me")
        val appTheme = booleanPreferencesKey(name = "app_theme")
        val language = stringPreferencesKey(name = "app_language")
    }

    suspend fun saveRememberMe(rememberMe :Boolean){
        context.dataStore.edit { preferences ->
            preferences[MyKeys.rememberMe] = rememberMe
        }
    }

    suspend fun saveTheme(theme :Boolean){
        context.dataStore.edit { preferences ->
            preferences[MyKeys.appTheme] = theme
        }
    }

    suspend fun saveLanguage(language :String){
        context.dataStore.edit { preferences ->
            preferences[MyKeys.language] = language
        }
    }

    val readRememberMe: Flow<Boolean> = context.dataStore.data.catch { e ->
        if(e is IOException){
            Log.e("DataStoreRepo", e.toString())
            emit(emptyPreferences())
        }else{
            throw e
        }
    }.map { value ->
    val myRemember = value[MyKeys.rememberMe] ?: false
        myRemember
    }

    val readTheme: Flow<Boolean> = context.dataStore.data.catch { e ->
        if(e is IOException){
            Log.e("DataStoreRepo", e.toString())
            emit(emptyPreferences())
        }else{
            throw e
        }
    }.map { value ->
    val theme = value[MyKeys.appTheme] ?: false
        theme
    }

    val readLanguage: Flow<String> = context.dataStore.data.catch { e ->
        if(e is IOException){
            Log.e("DataStoreRepo", e.toString())
            emit(emptyPreferences())
        }else{
            throw e
        }
    }.map { value ->
    val language = value[MyKeys.language] ?: "en"
        language
    }



}