package com.example.truthordaresaudi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.view.MotionEvent

import android.view.View.OnTouchListener
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
    private lateinit var ivProfile: ImageView
    private lateinit var icShare: ImageView
    private lateinit var icSettings: ImageView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    private val appUrl = "https://github.com/Tuwaiq-Jeddah-Kotlin-1/TruthOrDare-Game"


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


        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)


        tvStart.setOnClickListener {
           GlobalScope.launch (Dispatchers.Main){
               ibStart.startAnimation(scaleUp)
               delay(500)
               findNavController().navigate(R.id.action_homeFragment_to_playFragment)
           }
        }
        ibStart.setOnClickListener {
           GlobalScope.launch (Dispatchers.Main){
               ibStart.startAnimation(scaleUp)
               delay(500)
               findNavController().navigate(R.id.action_homeFragment_to_playFragment)
           }
        }
        ibBottle.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
        }
        tvBottle.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main){
                ibBottle.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_bottleFragment)
            }
        }
        ibRules.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main){
                ibRules.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
            }
        }
        tvRules.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main){
                ibRules.startAnimation(scaleUp)
                delay(500)
                findNavController().navigate(R.id.action_homeFragment_to_rulesFragment)
            }
        }
        ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        icSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)

        }
        icShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share App", appUrl)
            startActivity(intent)
        }
    }


}