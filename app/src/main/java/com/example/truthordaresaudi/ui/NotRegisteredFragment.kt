package com.example.truthordaresaudi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.R

class NotRegisteredFragment : Fragment() {

    private lateinit var registerTv: TextView
    private lateinit var registerBtn: ImageButton
    private lateinit var backBtn: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_registered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBtn = view.findViewById(R.id.registerBtn1)
        registerTv = view.findViewById(R.id.tvRegister1)
        backBtn = view.findViewById(R.id.backNotRegistered)

        registerTv.setOnClickListener {
            findNavController().navigate(R.id.action_notRegisteredFragment_to_RegisterFragment)

        }
        registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_notRegisteredFragment_to_RegisterFragment)

        }
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_notRegisteredFragment_to_homeFragment)
        }
    }

}