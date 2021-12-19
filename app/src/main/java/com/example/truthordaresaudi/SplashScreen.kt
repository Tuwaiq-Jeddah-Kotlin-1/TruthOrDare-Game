package com.example.truthordaresaudi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationRepo().myNotification(this)
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


        val sharedPreferencesSettings= this.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferencesSettings.getString("Settings","")

        if (language.toString()=="ar"){

            Toast.makeText(this,"arabic",Toast.LENGTH_LONG).show()
            setLocate()
        }else{
//            Toast.makeText(this,"Else",Toast.LENGTH_LONG).show()

        }

    }

    private fun setLocate(){
        val locale = Locale("ar")

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale

        //---------------------------------------------------------------
        this?.resources?.updateConfiguration(config, this.resources.displayMetrics)

    }


}