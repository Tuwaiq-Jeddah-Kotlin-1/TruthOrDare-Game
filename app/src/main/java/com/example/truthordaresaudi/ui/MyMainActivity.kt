package com.example.truthordaresaudi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R

class MyMainActivity : AppCompatActivity() {

    private lateinit var myVM: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val supportActionBar: ActionBar? = (this).supportActionBar
        supportActionBar?.hide()

        setContentView(R.layout.activity_my_main)

        myVM = ViewModelProvider(this).get(MyViewModel::class.java)
        myVM.readTheme.observe(this,{
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.e("SplashFragmentTheme","it = true")
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.e("SplashFragmentTheme","it = false")
            }
        })

         myVM.readLanguage.observe(this,{
          if (it == "ar"){
              Log.e("ProfileFragmentTheme","it = arabic")
              myVM.setLocale(this, "ar")

          }else{
              Log.e("ProfileFragmentTheme","it = english")
              myVM.setLocale(this, "en")
          }
//          findNavController(ProfileFragment).navigate(R.id.action_profileFragment_self)
      })

    }
    /*    supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile -> Toast.makeText(applicationContext, "Profile Clicked", Toast.LENGTH_LONG).show()
            }
            true
        }*/





   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }*/
}