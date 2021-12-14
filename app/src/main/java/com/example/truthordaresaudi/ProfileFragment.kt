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
import java.util.*

class ProfileFragment : Fragment() {

    private var langValue: String = "English"
    private val myVM = ViewModel()
    private lateinit var nameProfile: TextView
    private lateinit var emailProfile: TextView
    private lateinit var logOut: ImageView
    private lateinit var preferencesSettings: SharedPreferences
    private lateinit var preferences: SharedPreferences


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
        val spinnerCity: Spinner = view.findViewById(R.id.languageSpinner)

        preferences = this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        val emailPref = preferences.getString("EMAIL", "")
        emailProfile.text = emailPref
//        val passwordPref = preferences.getString("PASSWORD", "")

        logOut.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCity.adapter = adapter
        }
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                langValue = spinnerCity.selectedItem.toString()
                Toast.makeText(view.context, "Language selected successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        myVM.userInfo().observe(viewLifecycleOwner, {
            nameProfile.text = it.fullName
            emailProfile.text = it.email
        })

    }

    private fun showChangeLanguage(){
        val langItems = arrayOf("عربي","English")
        val mBuilder = AlertDialog.Builder(this.requireContext())

        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(langItems,-1){
                dialog, which ->
            if (which ==0){
                setGameLocale("ar")
            }else if (which==1){
                setGameLocale("en")
            }
            dialog.dismiss()
        }
        val mDialog =mBuilder.create()
        mDialog.show()
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