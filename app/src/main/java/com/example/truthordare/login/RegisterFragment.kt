package com.example.truthordare.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.truthordare.R
import com.example.truthordare.data.model.Users
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "Register"

class RegisterFragment : Fragment() {

    private lateinit var registerName: TextInputEditText
    private lateinit var registerEmail: TextInputEditText
    private lateinit var registerPassword: TextInputEditText
    private lateinit var registerConfirmPassword: TextInputEditText
    private lateinit var registerBtn: ImageButton
    //DB
    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerName = view.findViewById(R.id.etFullName)
        registerEmail = view.findViewById(R.id.etEmail)
        registerPassword = view.findViewById(R.id.etPassword)
        registerConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        registerBtn = view.findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(registerEmail.text.toString().trim()) -> {
                    Toast.makeText(context, "Please Enter Email", Toast.LENGTH_LONG).show()

                }

                TextUtils.isEmpty(registerPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_LONG).show()

                }
                TextUtils.isEmpty(registerConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please Enter Password Confirmation", Toast.LENGTH_LONG).show()

                }
                TextUtils.isEmpty(registerName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(context, "Please Enter Your Full Name", Toast.LENGTH_LONG).show()

                }
                else -> {
                    if (registerPassword.text.toString() == registerConfirmPassword.text.toString()) {
                        createUser()

                    } else {
                        Toast.makeText(this.context, "Password is not equal Password Confirmation", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun createUser(){

            auth.createUserWithEmailAndPassword(
                registerEmail.text.toString().trim().lowercase(),
                registerPassword.text.toString().trim()
            ).addOnCompleteListener { register ->

                if (register.isSuccessful) {
                    Toast.makeText(context, "You registered successfully  \uD83C\uDF89", Toast.LENGTH_LONG).show()
                    //successful authentication + generate UID
                    saveUserData(registerEmail.text.toString(), registerName.text.toString())

                } else {
                    Log.e(TAG, register.exception!!.message.toString())
                    Toast.makeText(context, register.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun saveUserData(emailU: String, nameU: String) {
        var uId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val user = Users(email = emailU,fullName = nameU, uid = uId)
        saveUserFireStore(user)
        findNavController().navigate(R.id.homeFragment)
    }

    private fun saveUserFireStore(newUser: Users) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            db.collection("Users").document("$uid").set(newUser).addOnSuccessListener {
//                Toast.makeText(context, "Successfully saved data.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("Exception", e.localizedMessage)
        }
    }
}
