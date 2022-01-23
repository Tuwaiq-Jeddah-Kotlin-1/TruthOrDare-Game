package com.example.truthordaresaudi.ui.useraccount

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.SharedViewModel
import com.google.android.material.textfield.TextInputEditText


class RegisterFragment : Fragment() {

    private lateinit var registerName: TextInputEditText
    private lateinit var registerEmail: TextInputEditText
    private lateinit var registerPassword: TextInputEditText
    private lateinit var registerConfirmPassword: TextInputEditText
    private lateinit var registerBtn: ImageButton
    private lateinit var backRegister: ImageView
    private lateinit var checkBox: CheckBox
    private lateinit var tvLoginRegister: TextView
    private lateinit var sharedVM: SharedViewModel
    var isRemembered = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        tvLoginRegister = view.findViewById(R.id.tvLoginRegister)
        backRegister = view.findViewById(R.id.backRegister)

        backRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }

        tvLoginRegister.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_loginFragment)
        }

        checkBox.setOnClickListener {
            isRemembered = !isRemembered
        }

        registerBtn.setOnClickListener {
            if (sharedVM.checkInternetConnection(view.context)) {
                when {
                    TextUtils.isEmpty(registerEmail.text.toString().trim()) -> {
                        Toast.makeText(context, getString(R.string.enter_email), Toast.LENGTH_LONG).show()
                    }
                    TextUtils.isEmpty(registerName.text.toString().trim()) -> {
                        Toast.makeText(context, getString(R.string.enter_name), Toast.LENGTH_LONG).show()
                    }
                    TextUtils.isEmpty(registerPassword.text.toString().trim()) -> {
                        Toast.makeText(context, getString(R.string.enter_password), Toast.LENGTH_LONG).show()
                    }
                    TextUtils.isEmpty(
                        registerConfirmPassword.text.toString().trim()
                    ) -> {
                        Toast.makeText(context, getString(R.string.enter_confirm_password), Toast.LENGTH_LONG).show()
                    }
                    registerPassword.text.toString() != registerConfirmPassword.text.toString() -> {
                        Toast.makeText(context, getString(R.string.passwords_should_match), Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        createUser()
                    }
                }
            } else {
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createUser() {
        val email = registerEmail.text.toString().trim()
        val password = registerPassword.text.toString().trim()
        val name = registerName.text.toString().trim()
        sharedVM.registerFirebase(
            email = email,
            password = password,
            isRemembered = isRemembered,
            registerName = name,
            lifecycleOwner = viewLifecycleOwner
        ).observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    context, getString(R.string.welcome) + registerName.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Toast.makeText(context, getString(R.string.register_failed), Toast.LENGTH_LONG).show()
            }
        }
    }


}
