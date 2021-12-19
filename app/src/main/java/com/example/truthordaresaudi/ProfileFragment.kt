package com.example.truthordaresaudi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ProfileFragment : Fragment() {

    private var langValue: String = "English"
    private val myVM = ViewModel()
    private lateinit var nameProfile: TextView
    private lateinit var emailProfile: TextView
    private lateinit var arLanguage: ImageView
    private lateinit var enLanguage: ImageView
    private lateinit var logOut: ImageView
    private lateinit var preferencesSettings: SharedPreferences
    private lateinit var preferences: SharedPreferences
    private val auth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameProfile = view.findViewById(R.id.tvFullName)
        emailProfile = view.findViewById(R.id.tvEmail)
        logOut = view.findViewById(R.id.profileLogoutIcon)
        arLanguage = view.findViewById(R.id.ivEnglish)
        enLanguage = view.findViewById(R.id.ivArabic)
//        val spinnerLanguage: Spinner = view.findViewById(R.id.languageSpinner)

        preferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        val emailPref =  emailProfile.text
        val namePref = preferences.getString("NAME", "")
        emailProfile.text = emailPref
        nameProfile.text = namePref


        logOut.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }


    }



    private fun setGameLocale(localeName: String) {

        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale

        context?.resources?.updateConfiguration(config, requireContext().resources.displayMetrics)
        preferencesSettings = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = preferencesSettings.edit()
        editor.putString("Settings", locale.toString())
        editor.apply()
        val refresh = Intent(context, SplashScreen::class.java)
        refresh.putExtra("currentLang", localeName)
        startActivity(refresh)
    }

}
