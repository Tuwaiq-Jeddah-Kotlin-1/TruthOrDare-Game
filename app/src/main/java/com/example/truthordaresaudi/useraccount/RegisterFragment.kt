package com.example.truthordaresaudi.useraccount

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.SharedViewModel
import com.example.truthordaresaudi.data.model.Users
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
    private lateinit var checkBox: CheckBox
    var isRemembered = false
    private lateinit var sharedVM : SharedViewModel


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

        sharedVM = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        registerName = view.findViewById(R.id.etFullName)
        registerEmail = view.findViewById(R.id.etEmail)
        registerPassword = view.findViewById(R.id.etPassword)
        registerConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        registerBtn = view.findViewById(R.id.registerBtn)
        checkBox = view.findViewById(R.id.cbRemember)

        checkBox.setOnClickListener {
            isRemembered = !isRemembered
        }

        registerBtn.setOnClickListener {
             if(sharedVM.checkInternetConnection(view.context)){
//                 Toast.makeText(context, "there is internet connection", Toast.LENGTH_LONG).show()


                 when {
                   /*  (!myVM.checkInternetConnection(view.context)) -> {
                         Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
                     }*/
                     TextUtils.isEmpty(registerEmail.text.toString().trim()) -> {
                         Toast.makeText(context, "Please Enter Email", Toast.LENGTH_LONG).show()

                     }
                     TextUtils.isEmpty(registerName.text.toString().trim { it <= ' ' }) -> {
                         Toast.makeText(context, "Please Enter Your Full Name", Toast.LENGTH_LONG).show()

                     }
                     TextUtils.isEmpty(registerPassword.text.toString().trim { it <= ' ' }) -> {
                         Toast.makeText(context, "Please Enter Password", Toast.LENGTH_LONG).show()

                     }
                     TextUtils.isEmpty(registerConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                         Toast.makeText(context, "Please Enter Password Confirmation", Toast.LENGTH_LONG).show()

                     }

                     registerPassword.text.toString() != registerConfirmPassword.text.toString() -> {
                         Toast.makeText(context, "Password Should Match Password Confirmation", Toast.LENGTH_LONG).show()
                     }

                     else -> {
                         createUser()
                     }
                 }
            }else{
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
            }


            /*  if(registerPassword.text.toString() == registerConfirmPassword.text.toString()) {
                createUser()
            }else{
                    Toast.makeText(context, "Password is not equal Password Confirmation", Toast.LENGTH_LONG).show()
                }*/
//checking connection
            /* if(registerPassword.text.toString() != registerConfirmPassword.text.toString()) {
//                createUser()
               Toast.makeText(context, "Password is not equal Password Confirmation", Toast.LENGTH_LONG).show()
           } else if(!myVM.noInternetConnection(view.context)) {
               createUser()
           }else{
               Toast.makeText(context, "You don't have internet connection", Toast.LENGTH_LONG).show()
           }*/


        }
    }

     private fun createUser(){
            auth.createUserWithEmailAndPassword(
                registerEmail.text.toString().trim().toLowerCase(),
                registerPassword.text.toString().trim()
            ).addOnCompleteListener { register ->
                if (register.isSuccessful) {
                    sharedVM.saveRememberMe(isRemembered)
                    sharedVM.getUserInfo()
                    Toast.makeText(context, "Welcome ${registerName.text.toString()} \uD83C\uDF89 !", Toast.LENGTH_LONG).show()
                    saveUserData(registerEmail.text.toString(), registerName.text.toString())

                } else {
                    Log.e(TAG, register.exception!!.message.toString())
                    Toast.makeText(context, register.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun saveUserData(emailU: String, nameU: String) {
        val uId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val user = Users(email = emailU,fullName = nameU, uid = uId)
        saveUserFireStore(user)
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun saveUserFireStore(newUser: Users) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            db.collection("Users").document("$uid").set(newUser).addOnSuccessListener {
            }
        } catch (e: Exception) {
            Log.e("Exception", e.localizedMessage)
        }
    }
}
