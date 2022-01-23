package com.example.truthordaresaudi.ui

import android.content.Intent
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
import com.example.truthordaresaudi.SharedViewModel
import com.example.truthordaresaudi.R
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var sharedVM: SharedViewModel
    private lateinit var saveName: ImageView
    private lateinit var deleteAccount: TextView
    private lateinit var backBtn: ImageView
    private val userAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val currentUser = userAuth.currentUser
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var curLang: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedVM = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val wiggle: Animation = AnimationUtils.loadAnimation(context, R.anim.wiggle)
        nameProfile = view.findViewById(R.id.tvFullName)
        emailProfile = view.findViewById(R.id.tvEmail)
        logOut = view.findViewById(R.id.profileLogoutIcon)
        arLanguage = view.findViewById(R.id.ivArabic)
        enLanguage = view.findViewById(R.id.ivEnglish)
        editName = view.findViewById(R.id.profileEditIcon)
        saveName = view.findViewById(R.id.saveName)
        deleteAccount = view.findViewById(R.id.deleteAccount)
        backBtn = view.findViewById(R.id.backProfile)

        saveName.visibility = View.GONE
        nameProfile.isEnabled = false


        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment2)
        }

        if (userId != null) {
            val info = sharedVM.userInfo()
            nameProfile.setText(info.fullName)
            emailProfile.text = info.email
        }

        val toggle: Switch = view.findViewById(R.id.switchTheme)

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedVM.saveTheme(true)
            } else {
                sharedVM.saveTheme(false)
            }
        }

        sharedVM.readTheme.observe(viewLifecycleOwner, {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.e("ProfileFragmentTheme", "it = true")
                toggle.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.e("ProfileFragmentTheme", "it = false")
                toggle.isChecked = false
            }
        })

        sharedVM.readLanguage.observe(viewLifecycleOwner, {
            curLang = it
            if (it == "ar") {
                Log.e("ProfileFragmentTheme", "it = arabic")
                sharedVM.setLocale(requireActivity(), "ar", viewLifecycleOwner)

            } else {
                Log.e("ProfileFragmentTheme", "it = english")
                sharedVM.setLocale(requireActivity(), "en", viewLifecycleOwner)
            }
        })

        logOut.setOnClickListener {
            sharedVM.saveRememberMe(false)
            userAuth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        arLanguage.setOnClickListener {
            arLanguage.startAnimation(wiggle)
            language("ar")
        }
        enLanguage.setOnClickListener {
            enLanguage.startAnimation(wiggle)
            language("en")
        }

        editName.setOnClickListener {
            nameProfile.isEnabled = true
            saveName.visibility = View.VISIBLE
            editName.visibility = View.GONE

            saveName.setOnClickListener {
                sharedVM.updateUser(nameProfile.text.toString())
                nameProfile.isEnabled = false
                saveName.visibility = View.GONE
                editName.visibility = View.VISIBLE
            }
        }

        deleteAccount.setOnClickListener {
            sharedVM.checkInternetConnection(view.context)
            deleteUserAccount(currentUser)
        }
    }

    private fun deleteUserAccount(currentUser: FirebaseUser?) {
        currentUser!!.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Your Account Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                fireStore.collection("Users").document(currentUser.uid).delete()
            }
        }.addOnFailureListener { e ->
            Log.e("addOnFailureListener", "addOnFailureListener", e)
        }
    }

    private fun language(langCode: String) {
        sharedVM.saveLanguage(langCode)
        sharedVM.setLocale(requireActivity(), langCode, viewLifecycleOwner)
        if (langCode != curLang) {
            startActivity(Intent(context, MyMainActivity::class.java))
        }
    }

}

