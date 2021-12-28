package com.example.truthordaresaudi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.notification.NotificationRepo

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        NotificationRepo().myNotification(this)
        setContentView(R.layout.splash_screen)
/*

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MyMainActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
*/


    }


}