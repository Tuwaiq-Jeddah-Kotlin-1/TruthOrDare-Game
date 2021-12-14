package com.example.truthordaresaudi.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginFragment : Fragment() {

    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPassword: TextInputEditText
    private lateinit var loginBtn: Button
    private lateinit var registerNow: TextView
    private lateinit var rememberMe: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false


    val userEmail: StringBuffer = StringBuffer()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginEmail = view.findViewById(R.id.etSignInEmail)
        loginPassword = view.findViewById(R.id.etSignInPassword)
        loginBtn = view.findViewById(R.id.loginBtn)
        registerNow = view.findViewById(R.id.registerNowBtn)
        rememberMe = view.findViewById(R.id.cbRemember)

        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if (isRemembered) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        registerNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(loginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please Enter Email", Toast.LENGTH_LONG).show()
                }

                TextUtils.isEmpty(loginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_LONG).show()

                }
                else -> {
                    val email: String = loginEmail.text.toString().trim { it <= ' ' }
                    val password: String = loginPassword.text.toString().trim { it <= ' ' }

                    // create an instance and create a register with email and passwords
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { operation ->
                            if (operation.isSuccessful) {
                                val emailPreference: String = email
                                val passwordPreference: String = password
                                val checked: Boolean = rememberMe.isChecked

                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("EMAIL", emailPreference)
                                editor.putString("PASSWORD", passwordPreference)
                                editor.putBoolean("CHECKBOX", checked)
                                editor.apply()
                                Toast.makeText(context, "Welcome \uD83C\uDF89", Toast.LENGTH_LONG).show()
                                findNavController().navigate(R.id.homeFragment)

                            } else {
                                Toast.makeText(
                                    context,
                                    operation.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }
}