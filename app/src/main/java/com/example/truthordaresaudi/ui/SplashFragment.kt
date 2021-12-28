package com.example.truthordaresaudi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import com.example.truthordaresaudi.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.MyViewModel


class SplashFragment : Fragment() {

    private lateinit var logo:ImageView
    private lateinit var myVM: MyViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //hide action bar
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
            supportActionBar?.hide()


        myVM = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        logo = view.findViewById(R.id.logo)


        logo.startAnimation(AnimationUtils.loadAnimation(context,R.anim.splash_in))
        Handler(Looper.getMainLooper()).postDelayed({

            logo.startAnimation(AnimationUtils

                .loadAnimation(context,R.anim.splash_out))
            Handler(Looper.getMainLooper()).postDelayed({
                logo.visibility = View.GONE


                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

            },0)
        },0)



    }


}