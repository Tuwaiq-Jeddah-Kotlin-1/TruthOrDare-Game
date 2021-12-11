package com.example.truthordaresaudi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
//        supportActionBar?.hide()
//        Handler().postDelayed({
//            val i = Intent(this, MyMainActivity::class.java)
//            startActivity(i)
//            finish()
//        }, 2000)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MyMainActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}