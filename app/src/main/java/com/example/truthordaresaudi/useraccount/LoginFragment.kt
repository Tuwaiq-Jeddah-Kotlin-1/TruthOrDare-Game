package com.example.truthordaresaudi.useraccount

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPassword: TextInputEditText
    private lateinit var loginBtn: ImageButton
    private lateinit var registerNow: TextView
    private lateinit var rememberMe: CheckBox
    private lateinit var myVM: MyViewModel
    private var isRemembered = false


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
        myVM = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)


        rememberMe.setOnClickListener {
            isRemembered = !isRemembered
        }

        registerNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginBtn.setOnClickListener {
            if (myVM.checkInternetConnection(view.context)) {
                when {
                    TextUtils.isEmpty(loginEmail.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(context, "Please Enter Email", Toast.LENGTH_LONG).show()
                    }

                    TextUtils.isEmpty(loginPassword.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(context, "Please Enter Password", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        signIn()
                    }
                }

                /* else -> {
                        val email: String = loginEmail.text.toString().trim { it <= ' ' }
                        val password: String = loginPassword.text.toString().trim { it <= ' ' }

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { operation ->
                                if (operation.isSuccessful) {
                                    myVM.saveRememberMe(isRemembered)
                                    myVM.getUserInfo()
                                    Toast.makeText(context, "Welcome again \uD83C\uDF89 !", Toast.LENGTH_LONG).show()
                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                } else {
                                    Toast.makeText(context, operation.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                                }
                            }

                 }*/
            } else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
            }
            /*     if(myVM.checkInternetConnection(view.context)) {
                 signIn()
             }else{
                 Toast.makeText(context, "You don't have internet connection", Toast.LENGTH_LONG).show()*/
//        }
        }
    }

    private fun signIn() {
        val email: String = loginEmail.text.toString().trim { it <= ' ' }
        val password: String = loginPassword.text.toString().trim { it <= ' ' }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { operation ->
                if (operation.isSuccessful) {
                    myVM.saveRememberMe(isRemembered)
                    myVM.getUserInfo()
                    Toast.makeText(context, "Welcome again \uD83C\uDF89 !", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(context, operation.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }


    }
}