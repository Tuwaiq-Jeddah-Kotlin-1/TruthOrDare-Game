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
import com.example.truthordaresaudi.SharedViewModel
import com.example.truthordaresaudi.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPassword: TextInputEditText
    private lateinit var loginBtn: ImageButton
    private lateinit var backLogin: ImageView
    private lateinit var rememberMe: CheckBox
    private lateinit var sharedVM: SharedViewModel
    private lateinit var tvRegisterLogin: TextView
    private lateinit var forgetPassword: TextView
    private lateinit var resetByEmail: EditText
    private lateinit var ibReset: ImageButton
    private lateinit var tvReset: TextView
    private var isRemembered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedVM = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        loginEmail = view.findViewById(R.id.etSignInEmail)
        loginPassword = view.findViewById(R.id.etSignInPassword)
        loginBtn = view.findViewById(R.id.loginBtn)
        rememberMe = view.findViewById(R.id.cbRemember)
        tvRegisterLogin = view.findViewById(R.id.tvRegisterLogin)
        backLogin = view.findViewById(R.id.backLogin)
        forgetPassword = view.findViewById(R.id.forgetPassword)


        forgetPassword.setOnClickListener {
            resetPasswordBottomSheet()
        }

        backLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        tvRegisterLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        rememberMe.setOnClickListener {
            isRemembered = !isRemembered
        }

        loginBtn.setOnClickListener {
            if (sharedVM.checkInternetConnection(view.context)) {
                when {
                    TextUtils.isEmpty(loginEmail.text.toString().trim()) -> {
                        Toast.makeText(context, getString(R.string.enter_email), Toast.LENGTH_LONG)
                            .show()
                    }
                    TextUtils.isEmpty(loginPassword.text.toString().trim()) -> {
                        Toast.makeText(
                            context,
                            getString(R.string.enter_password),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        signIn()
                    }
                }
            } else {
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun resetPasswordBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.reset_password, null)

        resetByEmail = view.findViewById(R.id.email_reset)
        ibReset = view.findViewById(R.id.ibReset)
        tvReset = view.findViewById(R.id.tvReset)
        val builder = BottomSheetDialog(requireView().context!!)
        builder.setTitle("Reset Password")
        builder.setContentView(view)
        builder.show()

        ibReset.setOnClickListener {
           val email = resetByEmail.text.toString().trim()
            sharedVM.resetPassword(email, viewLifecycleOwner).observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        context,
                        "Reset successfully, check your email!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Failed to reset, try again later..",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

       /* tvReset.setOnClickListener {
            Toast.makeText(
                context,
                "Reset successfully, check your email!",
                Toast.LENGTH_LONG
            ).show()
        }
        ibReset.setOnClickListener {
            Toast.makeText(
                context,
                "Reset successfully, check your email!",
                Toast.LENGTH_LONG
            ).show()
        }*/
    }

    private fun signIn() {
        val email: String = loginEmail.text.toString().trim()
        val password: String = loginPassword.text.toString().trim()
        sharedVM.loginFirebase(email, password, isRemembered, viewLifecycleOwner)
            .observe(viewLifecycleOwner, {
                if (it) {
                    Toast.makeText(context, getString(R.string.welcome_again), Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(context, getString(R.string.login_failed), Toast.LENGTH_LONG)
                        .show()
                }
            })

    }
}