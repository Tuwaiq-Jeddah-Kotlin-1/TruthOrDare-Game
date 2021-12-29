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
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.data.model.UserSuggestions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var ibStart: ImageButton
    private lateinit var ibBottle: ImageButton
    private lateinit var ibRules: ImageButton
    private lateinit var tvStart: TextView
    private lateinit var tvBottle: TextView
    private lateinit var tvRules: TextView
    private lateinit var ivLogin: ImageView
    private lateinit var icShare: ImageView
    private lateinit var profile: ImageView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var suggestion: TextView
    private lateinit var saveBtnAPI: ImageButton
    private lateinit var saveAPI: TextView
    private lateinit var loginText: TextView
    private lateinit var value: EditText
    val uId = FirebaseAuth.getInstance().currentUser?.uid
    val fb = FirebaseAuth.getInstance()


    private lateinit var myVM: MyViewModel
    private val appUrl = "https://github.com/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myVM = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        ibStart = view.findViewById(R.id.startBtn)
        ibBottle = view.findViewById(R.id.bottleBtn)
        ibRules = view.findViewById(R.id.rulesBtn)
        tvStart = view.findViewById(R.id.tvLogin)
        tvBottle = view.findViewById(R.id.tvBottle)
        tvRules = view.findViewById(R.id.tvRules)
        ivLogin = view.findViewById(R.id.loginIcon)
        icShare = view.findViewById(R.id.shareIcon)
        profile = view.findViewById(R.id.profile)
        loginText = view.findViewById(R.id.tv_login)
        suggestion = view.findViewById(R.id.tvSuggestion)

        var action = R.id.action_homeFragment_to_notRegisteredFragment


        myVM.readRememberMe.observe(viewLifecycleOwner, {
            if (it) {
                Log.e("HomeFragment", "it = true")
            } else if (myVM.isFirstTime) {
                Log.e("HomeFragment", "it = false")
                fb.signOut()
                ivLogin.visibility = View.VISIBLE
                loginText.visibility = View.VISIBLE
                action = R.id.action_homeFragment_to_notRegisteredFragment
            }
            myVM.isFirstTime = false
        })



        ivLogin.visibility = View.VISIBLE
        loginText.visibility = View.VISIBLE

        if (uId != null) {
            action = R.id.action_homeFragment_to_profileFragment
            ivLogin.visibility = View.INVISIBLE
            loginText.visibility = View.INVISIBLE
            myVM.getUserInfo()
        }


        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)


        tvStart.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                ibStart.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_playFragment)
            }
        }
        ibStart.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                ibStart.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_playFragment)
            }
        }
        ibBottle.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
        }
        tvBottle.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                ibBottle.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
            }
        }
        ibRules.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                ibRules.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
            }
        }
        tvRules.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                ibRules.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
            }
        }
        ivLogin.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        profile.setOnClickListener {
            findNavController().navigate(action)
        }
        icShare.setOnClickListener {
            shareApp()
        }
        suggestion.setOnClickListener {
            userSuggestion()
        }
    }

    private fun userSuggestion() {
        val view: View = layoutInflater.inflate(R.layout.suggestion, null)

        saveAPI = view.findViewById(R.id.tvSaveToAPI)
        saveBtnAPI = view.findViewById(R.id.saveToAPI)
        value = view.findViewById(R.id.suggestion)

        val builder = BottomSheetDialog(requireView().context!!)
        builder.setTitle("User Suggestion")
        builder.setContentView(view)

        builder.show()
        saveBtnAPI.setOnClickListener {
            saveToAPI(view)
        }
        saveAPI.setOnClickListener {
            saveToAPI(view)
        }
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/")
        startActivity(intent)
    }

    private fun saveToAPI(view: View) {
        val suggestions = UserSuggestions()
        suggestions.suggestion = value.text.toString()
        myVM.userRequests(suggestions, view.context, view)
        Toast.makeText(context, "Thank you for being a part of this game \uD83E\uDD73", Toast.LENGTH_LONG).show()
    }

}