package com.example.truthordare

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.fragment.findNavController
import com.example.truthordare.data.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var nameProfile: EditText
    private lateinit var emailProfile: TextView
    private lateinit var arLanguage: ImageView
    private lateinit var enLanguage: ImageView
    private lateinit var logOut: ImageView
    private lateinit var editProfile: ImageView
    private lateinit var preferences: SharedPreferences
    private val auth = FirebaseAuth.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wiggle: Animation = AnimationUtils.loadAnimation(context, R.anim.wiggle)
        nameProfile = view.findViewById(R.id.tvFullName)
        emailProfile = view.findViewById(R.id.tvEmail)
        logOut = view.findViewById(R.id.profileLogoutIcon)
        arLanguage = view.findViewById(R.id.ivArabic)
        enLanguage = view.findViewById(R.id.ivEnglish)
        editProfile = view.findViewById(R.id.profileEditIcon)
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        nameProfile.isEnabled = false

//        Log.e("userID", "$uid")

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            GlobalScope.launch(Dispatchers.IO) {
                db.collection("Users")
                    .document(uid!!)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val user = snapshot.toObject<Users>()
                        if (user != null) {
                            nameProfile.setText(user!!.fullName)
                            emailProfile.text = user!!.email
                        }
                    }
            }
        }


        var toggle: Switch = view.findViewById(R.id.switchTheme)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        logOut.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        arLanguage.setOnClickListener {
            arLanguage.startAnimation(wiggle)
            setLocale(requireActivity(), "ar")
        }

        enLanguage.setOnClickListener {
            enLanguage.startAnimation(wiggle)
            setLocale(requireActivity(), "en")
        }

        editProfile.setOnClickListener {
            nameProfile.isEnabled = true

        }

    }


    private fun setLocale(activity: Activity, languageCode: String) {
        val refresh = Intent(context, MyMainActivity::class.java)
        val locale = Locale(languageCode)
//        if(languageCode == Locale.)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        startActivity(refresh)
    }

//    fun userData(){
//        myVM.userInfo().observe(viewLifecycleOwner, {
//            nameProfile.text = it.fullName
//            emailProfile.text = it.email
//        })
//    }
}

