package com.example.truthordaresaudi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController


class PlayFragment : Fragment(), View.OnClickListener {

    lateinit var ibTruth: ImageButton
    lateinit var ibDare: ImageButton
    lateinit var ibPenalty: ImageButton
    lateinit var tvTruth: TextView
    lateinit var tvDare: TextView
    lateinit var tvPenalty: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibTruth = view.findViewById(R.id.truthBtn)
        ibDare = view.findViewById(R.id.dareBtn)
        ibPenalty = view.findViewById(R.id.penaltyBtn)
        tvTruth = view.findViewById(R.id.tvTruth)
        tvDare = view.findViewById(R.id.tvDare)
        tvPenalty = view.findViewById(R.id.tvPenalty)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.truthBtn, R.id.tvTruth -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
//            R.id.dareBtn, R.id.tvDare -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
//            R.id.penaltyBtn, R.id.tvPenalty -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)


        }
    }

}