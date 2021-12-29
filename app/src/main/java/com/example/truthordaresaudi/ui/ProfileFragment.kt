package com.example.truthordaresaudi.ui

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private lateinit var nameProfile: EditText
    private lateinit var emailProfile: TextView
    private lateinit var arLanguage: ImageView
    private lateinit var enLanguage: ImageView
    private lateinit var logOut: ImageView
    private lateinit var editName: ImageView
    private lateinit var myVM : MyViewModel
    private lateinit var saveName : ImageView
    private lateinit var deleteAccount : TextView

    private val userAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val currentUser = userAuth.currentUser
    private val userId = FirebaseAuth.getInstance().currentUser?.uid



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myVM = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        val wiggle: Animation = AnimationUtils.loadAnimation(context, R.anim.wiggle)
        nameProfile = view.findViewById(R.id.tvFullName)
        emailProfile = view.findViewById(R.id.tvEmail)
        logOut = view.findViewById(R.id.profileLogoutIcon)
        arLanguage = view.findViewById(R.id.ivArabic)
        enLanguage = view.findViewById(R.id.ivEnglish)
        editName = view.findViewById(R.id.profileEditIcon)
        saveName = view.findViewById(R.id.saveName)
        deleteAccount = view.findViewById(R.id.deleteAccount)



        saveName.visibility = View.GONE

        nameProfile.isEnabled = false

        if (userId != null) {
            nameProfile.setText(myVM.userInfo.fullName)
            emailProfile.text = myVM.userInfo.email
        }


        val toggle: Switch = view.findViewById(R.id.switchTheme)

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                myVM.saveTheme(true)
            } else {
//                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                myVM.saveTheme(false)
            }
        }

        myVM.readTheme.observe(viewLifecycleOwner,{
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.e("ProfileFragmentTheme","it = true")
                toggle.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.e("ProfileFragmentTheme","it = false")
                toggle.isChecked = false

            }
        })

       /* myVM.readLanguage.observe(viewLifecycleOwner,{
            if (it == "ar"){
                Log.e("ProfileFragmentTheme","it = arabic")
                setLocale(requireActivity(), "ar")

            }else{
                Log.e("ProfileFragmentTheme","it = english")
                setLocale(requireActivity(), "en")
            }
        })*/

        logOut.setOnClickListener {
            myVM.saveRememberMe(false)
            userAuth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        arLanguage.setOnClickListener {
            arLanguage.startAnimation(wiggle)
            myVM.saveLanguage("ar")
            setLocale(requireActivity(), "ar")
        }
        enLanguage.setOnClickListener {
            enLanguage.startAnimation(wiggle)
            myVM.saveLanguage("en")
            setLocale(requireActivity(), "en")
        }

        editName.setOnClickListener {
            nameProfile.isEnabled = true
            saveName.visibility = View.VISIBLE
            editName.visibility = View.GONE

            saveName.setOnClickListener {
                myVM.updateUser(nameProfile.text.toString())
                nameProfile.isEnabled = false
                saveName.visibility = View.GONE
                editName.visibility = View.VISIBLE
            }
        }

        deleteAccount.setOnClickListener {
            myVM.checkConnection(view.context)
            deleteUserAccount(currentUser)
        }
    }

    private fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        findNavController().navigate(R.id.action_profileFragment_self)
    }

    private fun deleteUserAccount(currentUser : FirebaseUser?){
        currentUser!!.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Your profile deleted successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                fireStore.collection("Users").document(currentUser.uid).delete()
            }
        }.addOnFailureListener { e ->
            Log.e("addOnFailureListener", "addOnFailureListener", e)
        }
    }

}

