package com.example.truthordaresaudi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    lateinit var ibStart: ImageButton
    lateinit var ibBottle: ImageButton
    lateinit var ibRules: ImageButton
    lateinit var tvStart: TextView
    lateinit var tvBottle: TextView
    lateinit var tvRules: TextView
    lateinit var ivProfile: ImageView
    lateinit var icShare: ImageView
    lateinit var icSettings: ImageView
    val appUrl = "https://github.com/Tuwaiq-Jeddah-Kotlin-1/TruthOrDare-Game"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibStart = view.findViewById(R.id.startBtn)
        ibBottle = view.findViewById(R.id.bottleBtn)
        ibRules = view.findViewById(R.id.rulesBtn)
        tvStart = view.findViewById(R.id.tvStart)
        tvBottle = view.findViewById(R.id.tvBottle)
        tvRules = view.findViewById(R.id.tvRules)
        ivProfile = view.findViewById(R.id.profile)
        icShare = view.findViewById(R.id.shareIcon)
        icSettings = view.findViewById(R.id.settingsIcon)

        ibStart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_playFragment)
        }
        tvStart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_playFragment)
        }
        ibBottle.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
        }
        tvBottle.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
        }
        ibRules.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
        }
        tvRules.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
        }
        ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        icSettings.setOnClickListener {
//            if(ui == userUid){
//                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
//            }else
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        icShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra("Share App", appUrl)
            startActivity(intent)
        }
    }

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.startBtn, R.id.tvStart -> findNavController().navigate(R.id.action_homeFragment_to_playFragment)
//            R.id.bottleBtn, R.id.tvBottle -> findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
////            R.id.shareBtn, R.id.tvShare -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
//
//
//        }
//    }
}